'use strict';

var angular = require('angular');
var PasswordResetController = require("./PasswordResetController");

var passwordResetModule = angular.module('password-reset', []);

passwordResetModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/password-reset', {
    controller: PasswordResetController,
    template: require('./password_reset.html')
  });
}]);

module.exports = passwordResetModule;
