'use strict';

var fs = require("fs");
var angular = require('angular');
var ContactsController = require("./ContactsController");

var contactsModule = angular.module('contacts', []);

contactsModule.controller("ContactsController", ContactsController);

contactsModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/contacts', {
    controller: ContactsController,
    template: fs.readFileSync(__dirname + '/contacts.html', 'utf-8')
  });
}]);

module.exports = contactsModule;
