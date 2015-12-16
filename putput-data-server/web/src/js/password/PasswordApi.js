'use strict';

function PasswordApi(http, api) {
  this.requestNewPassword = function(email) {
    return api.withLink("password-reset", function (resetLink) {
      return http.post(resetLink, {
        "email": email
      });
    });
  };

  this.confirmPasswordRequest = function(code) {
    return api.withLink("password-reset-confirmation", function (resetLink) {
      return http.post(resetLink, {
        "code": code
      });
    });
  };
}

PasswordApi.$inject = ["$http", "api"];

module.exports = PasswordApi;
