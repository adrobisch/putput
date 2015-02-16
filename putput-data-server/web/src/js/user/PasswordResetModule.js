'use strict';

var fs = require("fs");
var angular = require('angular');
var PasswordResetController = require("./PasswordResetController");

var passwordResetModule = angular.module('password-reset', []);

passwordResetModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/password-reset', {
    controller: PasswordResetController,
    template: fs.readFileSync(__dirname + '/password_reset.html', 'utf-8')
  });
}]);

module.exports = passwordResetModule;
