'use strict';

var fs = require("fs");
var angular = require('angular');
var HomeController = require("./HomeController");

var homeModule = angular.module('home', []);

homeModule.controller("HomeController", HomeController);

homeModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {
    controller: HomeController,
    template: fs.readFileSync(__dirname + '/home.html', 'utf-8')
  });
}]);

module.exports = homeModule;
