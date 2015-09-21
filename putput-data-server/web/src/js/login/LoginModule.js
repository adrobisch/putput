'use strict';

var angular = require('angular');
var LoginController = require("./LoginController");

var loginModule = angular.module('login', []);

loginModule.controller("LoginController", LoginController);

loginModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    controller: LoginController,
    template: require('./login.html')
  });
}]);

module.exports = loginModule;
