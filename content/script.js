
var app = angular.module('jputils', ['ui.bootstrap', 'ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider.when('/browser', {
        templateUrl: 'browser.html',
        controller: 'DirectoryController'
    }).when('/quiz/:depth/:type/:id/:source', {
        templateUrl: 'quiz/quiz.html',
        controller: 'QuizController'
    }).when('/test', {
        template: '<div>haha benis!<br /><img src="https://ih0.redbubble.net/image.96844221.7537/mp,550x550,gloss,ffffff,t.3.jpg" /></div>'
    }).when('/search/:word', {
        templateUrl: 'search/searchResults.html',
        controller: 'searchController'
    }).when('/songs', {
        templateUrl: 'songsAnalysis/songsAnalysis.html',
        controller: 'songsController'
    }).otherwise({
        redirectTo: '/browser'
    });
});

app.service('DirectoryService', ['$http', '$q', function ($http, $q){
    this.getList = function() {
        return $http.get('api/browser')
            .then(returned => returned.data);
    };
    this.getSubList = function(id) {
        return $http.get('api/browser/subitems/'+id)
            .then(returned => returned.data);
    };
    this.getDetails = function(dir) {
        return $http.get('api/browser/item/'+dir)
            .then(returned => returned.data);
    };
    this.getSubDetails = function(dir, file) {
        return $http.get('api/browser/subitem/'+dir+'/'+file)
            .then(returned => returned.data);
    };
}]);

app.controller('DirectoryController', ['DirectoryService', '$scope', '$location', function (DirectoryService, $scope, $location){
    DirectoryService.getList().then(function (list) {
        $scope.directories = list;
    });
    $scope.currentItem = "";
    $scope.directories = [];

    $scope.activities = ["reading", "listening"];
    $scope.phases = ["unprocessed", "black", "white"];
    $scope.data = {};
    $scope.activities.forEach(function (activity) {
        $scope.data[activity] = {};
        $scope.phases.forEach(function (phase) {
            $scope.data[activity][phase] = [];
        });
    });

    $scope.previewDepth = "";
    $scope.previewCategory = function (dir) {
        DirectoryService.getDetails(dir)
            .then(applyDetails);
        $scope.previewDepth = "item";
        $scope.currentItem = dir;
    };
    $scope.previewSubCategory = function (file) {
        DirectoryService.getSubDetails($scope.attachedTo, file)
            .then(applyDetails);
        $scope.previewDepth = "subitem";
        $scope.currentItem = file;
    };
    function applyDetails (details) {
        Object.keys(details).forEach(function (key) {
            $scope.data[key] = details[key];
        });
        generateSummary();
    }
    $scope.makeExam = function (depth, type, id, source) {
        $location.path('quiz/'+depth.toLowerCase()+'/'+type.toLowerCase()+'/'+id.toLowerCase()+'/'+source.toLowerCase());
    };
    $scope.subitems = [];
    $scope.attachedTo = "";
    $scope.depth = function () {
        if ($scope.attachedTo == "") {
            return "item";
        } else {
            return "subitem";
        }
    };
    $scope.expand = function (dir) {
        DirectoryService.getSubList(dir).then(function (list) {
            $scope.attachedTo = dir;
            $scope.subitems = list;
        });
    };
    $scope.summary = [];
    function generateSummary() {
        $scope.summary = [];
        ['reading', 'listening'].forEach(function (type){
            ['Unprocessed', 'Black', 'White'].forEach(function (source) {
                $scope.summary.push({
                    source: source,
                    type: type,
                    size: $scope.data[type][source.toLowerCase()].length
                });
            });
        });
    }
}]);

