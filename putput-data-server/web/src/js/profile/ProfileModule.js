'use strict';

var angular = require('angular');
var ProfileController = require("./ProfileController");

var ProfileModule = angular.module('profile', []);

ProfileModule.service("profile", require("./ProfileApi"));

ProfileModule.controller("ProfileController", ProfileController);

ProfileModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/profile/:userId', {
        controller: ProfileController,
        template: require('./profile.html')
    });
}]);

module.exports = ProfileModule;