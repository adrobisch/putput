'use strict';

var angular = require('angular');
var PasswordResetController = require("./PasswordResetController");

var passwordResetModule = angular.module('password-reset', []);

passwordResetModule.service("password", require("./PasswordApi"));

passwordResetModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/password-reset', {
    controller: PasswordResetController,
    template: require('./password_reset.html')
  }).when('/password-reset/confirmation/:code', {
    controller: PasswordResetController,
    template: require('./password_reset_confirm.html')
  })
}]);

module.exports = passwordResetModule;
