'use strict';

var angular = require('angular');
var searchModule = angular.module('search', []);

searchModule.controller("SearchBarController", require('./SearchBarController'));
searchModule.service("search", require('./SearchService'));

module.exports = searchModule;
