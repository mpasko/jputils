
var app = angular.module('jputils', ['ui.bootstrap', 'ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider.when('/browser', {
        templateUrl: 'browser.html',
        controller: 'DirectoryController'
    }).when('/quiz/:depth/:type/:id/:source', {
        templateUrl: 'quiz/quiz.html',
        controller: 'QuizController'
    }).when('/test', {
        template: '<div>haha benis!</div>'
    }).when('/search/:word', {
        templateUrl: 'searchResults.html',
        controller: 'searchController'
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
    
    $scope.readingUnprocessed = [];
    $scope.listeningUnprocessed = [];
    $scope.readingBlack = [];
    $scope.listeningBlack = [];
    $scope.readingWhite = [];
    $scope.listeningWhite = [];
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
            $scope[key] = details[key];
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
                    size: $scope[type+source].length
                });
            });
        });
    }
}]);

app.component('preview', {
    bindings: {
        previewData:"="
    },
    templateUrl: "previewTemplate.html"
});

function directoryTableController($scope, $element, $attrs) {
  var $ctrl = this;
  $ctrl.types = ['reading', 'listening']; 
  $ctrl.sources = ['unprocessed', 'black'];
  $ctrl.buttons = [];
  $ctrl.types.forEach(function(type) {
      $ctrl.sources.forEach(function(source) {
          $ctrl.buttons.push({type:type, source:source});
      });
  });
}

app.component('directoryTable', {
    bindings: {
        depth: "@",
        dir: "=",
        makeExam:"=",
        previewCategory:"=",
        expand:"=",
        expanded:"="
    },
    replace: true,
    transclude: true,
    controller: directoryTableController,
    templateUrl: "directoryTable.html"
});