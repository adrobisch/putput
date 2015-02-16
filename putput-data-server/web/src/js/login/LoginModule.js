'use strict';

var fs = require("fs");
var angular = require('angular');
var LoginController = require("./LoginController");

var loginModule = angular.module('login', []);

loginModule.controller("LoginController", LoginController);

loginModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    controller: LoginController,
    template: fs.readFileSync(__dirname + '/login.html', 'utf-8')
  });
}]);

module.exports = loginModule;
