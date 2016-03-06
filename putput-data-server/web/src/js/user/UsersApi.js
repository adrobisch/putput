function UsersApi(http, api) {

  var userSettingsRelation = "user-settings";
  var userFeedsRelation = "user-feeds";
  var userFeedRelation = "user-feed";
  
  this.addUser = function (user) {
    return api.withLink("user", function (userLink) {
      return http.post(userLink, user);
    });
  };
  
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
    return api.withLink("user-info", function (userLink) {
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
  
  this.getAccessTokens = function () {
    return api.withLink("access-tokens", function (accessTokensLinks) {
      return http.get(accessTokensLinks);
    })
  };

  this.addAccessToken = function(clientId, expiryDate) {
    return api.withLink("access-token", function (accessTokenLink) {
      return http.post(accessTokenLink.replace("{id}", ""), {
        "clientId": clientId,
        "expiryDate": expiryDate
      });
    })
  };

  this.deleteAccessToken = function(accessTokenId) {
    return api.withLink("access-token", function (accessTokenLink) {
      return http.delete(accessTokenLink.replace("{id}", accessTokenId));
    })
  };

  this.getRssFeeds = function() {
    return api.withLink(userFeedsRelation, function (userFeedsLink) {
      return http.get(userFeedsLink);
    })
  };

  this.addRssFeed = function(url) {
    return api.withLink(userFeedsRelation, function (userFeedsLink) {
      return http.post(userFeedsLink, {
        "url": url
      });
    })
  };

  this.deleteRssFeed = function(feedId) {
    return api.withLink(userFeedRelation, function (userFeedLink) {
      return http.delete(userFeedLink+"/"+feedId);
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