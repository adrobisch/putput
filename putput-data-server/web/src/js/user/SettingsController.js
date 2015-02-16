var SettingsController = function (scope, users, alertService) {

  scope.settings;

  scope.updateSettings = function () {
    users.updateSettings(scope.settings).success(function () {
      alertService.info("Done", "Settings updated!", 2000);
    });
  };

  scope.getSettings = function () {
    users.getSettings().success(function (response){
      scope.settings = response.data;
    });
  };

};
SettingsController.$inject = ["$scope", "users", "$alert"];
module.exports = SettingsController;