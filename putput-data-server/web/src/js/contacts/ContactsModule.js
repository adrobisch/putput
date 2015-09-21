'use strict';

var angular = require('angular');
var ContactsController = require("./ContactsController");
var ContactDetailsController = require("./ContactDetailsController");

var contactsModule = angular.module('contacts', []);
contactsModule.service('contacts', require('./ContactsApi'));

contactsModule.controller("ContactsController", ContactsController);

contactsModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/contacts', {
    controller: ContactsController,
    template: require('./contacts.html')
  }).when('/contact/:id', {
    controller: ContactDetailsController,
    template: require('./contact_details.html')
  });
}]);

module.exports = contactsModule;
