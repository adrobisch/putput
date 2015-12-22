function UsersApi(http, api) {

  var userSettingsRelation = "user-settings";

  this.getUsers = function (userListCallback, search) {
    return api.withLink("users", this.getUsersFromUrl(userListCallback, search));
  };

  this.getUsersFromUrl = function (userListCallback, search) {
    return function (url) {
      var requestUrl = search ? url + "?search=" + search : url;
      return http.get(requestUrl).success(userListCallback);
    };
  };

  this.userInfo = function (userInfoCallback) {
    return api.withLink("user", function (userLink) {
      return http.get(userLink).success(userInfoCallback);
    });
  };

  this.requestNewPassword = function(email) {
    return api.withLink("password-reset", function (resetLink) {
      return http.post(resetLink, {
        "email": email
      });
    })
  };

  this.getSettings = function() {
    return api.withLink(userSettingsRelation, function (settingsLink) {
      return http.get(settingsLink);
    })
  };

  this.updateSettings = function(settings) {
    return api.withLink(userSettingsRelation, function (settingsLink) {
      return http.put(settingsLink, settings);
    })
  };
}

UsersApi.$inject = ["$http", "api"];

module.exports = UsersApi;