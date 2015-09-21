'use strict';
var angular = require('angular');

var usersModule = angular.module('users', []);

usersModule.service('users', require('./UsersApi'));

module.exports = usersModule;