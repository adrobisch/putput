var ResetPasswordController = function (scope, admin, alertService, location) {
  scope.requestNewPassword = function () {
    admin.requestNewPassword(scope.email).success(function (success) {
      alertService.info("Password Reset!", "Please login using the new password.");
      location.path("/login");
    }).error(function (error) {
      console.log(error);
      alertService.error("Error", "Unable to request a new password for this email, did you enter it correctly?");
    });
  };
};
ResetPasswordController.$inject = ["$scope", "users", "$alert", "$location"];
module.exports = ResetPasswordController;