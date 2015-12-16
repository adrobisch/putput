'use strict';

var ResetPasswordController = function (scope, password, alertService, location, routeParams) {
  scope.requestNewPassword = function () {
    password.requestNewPassword(scope.email).success(function () {
      alertService.info("Password Requested!", "Please check your mail " +
          "and follow the instructions to reset your password.");
      location.path("/login");
    }).error(function (error) {
      console.log(error);
      alertService.error("Error", "Unable to request a new password for this email, did you enter it correctly?");
    });
  };

  scope.confirm = function () {
    var confirmationCode = routeParams.code;

    if (confirmationCode) {
      password.confirmPasswordRequest(confirmationCode).success(function () {
        alertService.info("Password Reset!", "Please login using the new password.");
        location.path("/login");
      }).error(function (error) {
        console.log(error);
        alertService.error("Error", "Unable to reset the password.");
      });
    }
  };
};

ResetPasswordController.$inject = ["$scope", "password", "$alert", "$location", "$routeParams"];
module.exports = ResetPasswordController;