app.service('QuizService', ['$http', '$q', function ($http, $q){
    this.getReadingQuiz = function(depth, type, id, source) {
        return $http.get('api/exam/'+depth+'/'+type+'/'+id+'/'+source).then(returned => returned.data);
    };
    
    this.saveResults = function(type, name, black, white) {
        $http.put('api/save/black/'+type+'/'+name, black);
        $http.put('api/save/white/'+type+'/'+name, white);
    };
}]);

app.controller('QuizController', ['$scope', '$routeParams', 'QuizService', '$location', function ($scope, $routeParams, QuizService, $location) {
    var quizData;
    var nodeService = {
        getCurrentWord: () => "",
        shouldFinish: () => false
    };
    QuizService.getReadingQuiz($routeParams.depth, $routeParams.type, $routeParams.id, $routeParams.source)
    .then(function (data) {
        quizData = data;
        nodeService = window.buildQuiz(convertToNode(quizData), []);
        console.log(quizData);
        console.log(nodeService);
        rewriteStats();
    });
    $scope.incorrectCount = 0;
    $scope.correctCount = 0;
    $scope.totalCount = 0;
    $scope.mistakes = [];
    $scope.meaning = {};
    $scope.current = "";
    
    $scope.agregateResults = function () {
        
    };
    
    $scope.saveResults = function () {
        var black = nodeService.convertResultsIntoTest();
        var white = nodeService.getWhitelistAsText();
        QuizService.saveResults($routeParams.type, $routeParams.id, black, white);
    };
    
    $scope.shouldFinish = function () {
        return nodeService.shouldFinish();
    }
    
    $scope.next = function() {
        if ($scope.meaning.text === undefined) {
            $scope.meaning.text = "";
        }
        nodeService.stepNext($scope.meaning.text);
        rewriteStats();
        $scope.meaning.text = "";
    };
    
    $scope.cancel = function () {
        $location.path('/');
    };
    
    $scope.revertWord = function (mistake) {
        nodeService.revertWord(mistake.word, mistake.expected);
        rewriteStats();
    };
    
    document.onkeypress = function(e){
        if (e.which == 13){
            $scope.next();
            $scope.$apply();
        }
    };
    
    function rewriteStats() {
        var stats = nodeService.getStats();
        $scope.incorrectCount = stats.errors;
        $scope.correctCount = stats.correct;
        $scope.totalCount = stats.total;
        $scope.mistakes = convertFromNode(nodeService.getResults());
        $scope.current = nodeService.getCurrentWord();
    };
    
    function convertToNode(data) {
        return data.map(function (item) {
            return {
                key: item.question,
                value: item.corectAnswer
            };
        });
    };
    
    function convertFromNode(data) {
        return data.map(function (item) {
            return {
                word:item.key,
                expected:item.value,
                actual:item.actual
            };
        });
    };
}]);

document.onload = () => document.getElementById('meaning').focus();