'use strict';

var fs = require("fs");
var angular = require('angular');
var ContactsController = require("./ContactsController");
var ContactDetailsController = require("./ContactDetailsController");

var contactsModule = angular.module('contacts', []);

contactsModule.controller("ContactsController", ContactsController);

contactsModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/contacts', {
    controller: ContactsController,
    template: fs.readFileSync(__dirname + '/contacts.html', 'utf-8')
  }).when('/contact/:id', {
    controller: ContactDetailsController,
    template: fs.readFileSync(__dirname + '/contact_details.html', 'utf-8')
  });
}]);

module.exports = contactsModule;
