var LoginController = function(scope, auth, location, alerts) {
  scope.username = "";
  scope.password = "";

  scope.login = function () {
    auth.authenticate(scope.username, scope.password).then(function onSuccess() {
      location.path("/home");
    }, function onError() {
      alerts.error("Invalid", "Please check entered credentials.");
    })
  };
  
  scope.logout = function () {
    auth.logout().then(function onSuccess() {
      location.path("/login");
    });
  };

};

LoginController.$inject = ["$scope", "auth", "$location", "$alert"];

module.exports = LoginController;