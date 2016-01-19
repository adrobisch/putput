var SettingsController = function (scope, users, alertService) {

  scope.updateSettings = function () {
    users.updateSettings(scope.settings).success(function () {
      alertService.info("Done", "Settings updated!", 2000);
    });
  };

  scope.addRssFeed = function () {
    users.addRssFeed(scope.newFeedUrl).success(scope.getRssFeeds);
  };

  scope.deleteRssFeed = function (feedId) {
    users.deleteRssFeed(feedId).success(scope.getRssFeeds);
  };

  scope.getSettings = function () {
    users.getSettings().success(function (response){
      scope.settings = response.data;
    });
  };

  scope.getRssFeeds = function () {
    users.getRssFeeds().success(function (response){
      scope.rssFeedList = response.data;
    });
  };

};
SettingsController.$inject = ["$scope", "users", "$alert"];
module.exports = SettingsController;