function toFormData(obj) {
  var str = [];
  for(var p in obj)
    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
  return str.join("&");
}

function AuthenticationApi(http, api) {
  this.authenticate = function (login, password) {
    return api.withLink("login", function (loginLink) {
      return http({
        method: 'POST',
        url: loginLink,
        data: toFormData({"username" : login, "password" : password}),
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
      });
    });
  };

  this.logout = function () {
    return api.withLink("logout", function(logoutLink) {
      return http.post(logoutLink, {});
    });
  };
}

AuthenticationApi.$inject = ["$http", "api"];

module.exports = AuthenticationApi;