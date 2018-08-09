
app.service('SearchService', ['$http', '$q', function ($http, $q){
    this.search = function(word) {
        return $http.get('api/search/'+word)
            .then(returned => returned.data);
    };
}]);

app.controller('searchController', ['SearchService', '$scope', '$routeParams', 
    function (SearchService, $scope, $routeParams){
    SearchService.search($routeParams.word).then(function (results) {
        $scope.results = Object.keys(results).map(function (key) {
            return {
                path: key.replace("improved_workflow/words/",""),
                value: results[key]
            };
        });
    });
    
}]);