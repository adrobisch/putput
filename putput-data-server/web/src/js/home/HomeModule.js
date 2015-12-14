'use strict';

var angular = require('angular');
var HomeController = require("./HomeController");

var homeModule = angular.module('home', [
    require("../timeline/TimelineModule").name
]);

homeModule.controller("HomeController", HomeController);

homeModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    controller: HomeController,
    template: require("./home.html")
  });
}]);

module.exports = homeModule;
