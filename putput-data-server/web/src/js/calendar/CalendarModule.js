'use strict';

var angular = require('angular');
var CalendarController = require("./CalendarController");

var calendarModule = angular.module('calendar', []);

calendarModule.service("calendar", require('./CalendarApi'));
calendarModule.controller("CalendarController", CalendarController);

calendarModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/calendar', {
    controller: CalendarController,
    template: require('./calendar.html')
  });
}]);

module.exports = calendarModule;
