'use strict';

var angular = require('angular');
var SignupController = require("./SignupController");

var signupModule = angular.module('signup', []);

signupModule.controller("SignupController", SignupController);

signupModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/signup', {
    controller: SignupController,
    template: require('./signup.html')
  });
}]);

module.exports = signupModule;
