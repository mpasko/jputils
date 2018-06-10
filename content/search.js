function searchController($scope, $location) {
  $scope.query = {text:""};
  $scope.performSearch = function () {
    var url = 'search/'+$scope.query.text;
    //$location.path(url);
    window.open("#!/"+url, '_blank');
  };
}

app.component('search', {
    templateUrl: "searchTemplate.html",
    controller: searchController
});
