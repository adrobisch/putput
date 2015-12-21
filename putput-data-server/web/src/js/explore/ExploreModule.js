'use strict';

var angular = require('angular');
var ExploreController = require("./ExploreController");

var userListModule = require("../user/UserListModule");

var exploreModule = angular.module('explore', [
    userListModule.name
]);

exploreModule.controller("ExploreController", ExploreController);

exploreModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/explore', {
        controller: ExploreController,
        template: require('./explore.html')
    });
}]);

module.exports = exploreModule;
