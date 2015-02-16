var HomeController =  function($scope, users) {
  users.userInfo(function withUserInfo(response){
    $scope.user = response.displayName;
  });
};

HomeController.$inject = ['$scope', 'users'];

module.exports = HomeController;