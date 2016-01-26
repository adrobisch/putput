var SignupController = function(scope, users, location, alerts) {
  scope.newUser = {
    "userName": null,
    "password": null,
    "email": null
  };
  
  scope.signup = function () {
    users.addUser(scope.newUser).success(function () {
      location.path("/home");
    }).error(function () {
      alerts.error("Error", "Unable to add user, try a different username");
    });
  };
  
};

SignupController.$inject = ["$scope", "users", "$location", "$alert"];

module.exports = SignupController;