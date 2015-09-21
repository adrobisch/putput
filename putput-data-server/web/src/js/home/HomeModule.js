'use strict';

var angular = require('angular');
var HomeController = require("./HomeController");

var homeModule = angular.module('home', []);

homeModule.controller("HomeController", HomeController);

homeModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {
    controller: HomeController,
    template: require('./home.html')
  });
}]);

module.exports = homeModule;
