app.service('SongService', ['$http', '$q', function ($http, $q){
    this.getData = function() {
        return $http.get('api/songs').then(result => result.data);
    };
}]);

app.controller('songsController', ['SongService', '$scope', '$routeParams',
    function (SongService, $scope, $routeParams){
    $scope.classifications = [];
    $scope.audio = [];
    SongService.getData().then(function (results) {
        console.log(results);
        $scope.classifications = results.classifications;
    });

}]);