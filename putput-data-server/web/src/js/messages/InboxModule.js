'use strict';

var angular = require('angular');
var InboxController = require("./InboxController");

var inboxModule = angular.module('inbox', []);

inboxModule.service("messages", require('./MessagesApi'));
inboxModule.controller("InboxController", InboxController);

inboxModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/inbox/:page?', {
    controller: InboxController,
    template: require('./inbox.html')
  });
}]);

module.exports = inboxModule;
