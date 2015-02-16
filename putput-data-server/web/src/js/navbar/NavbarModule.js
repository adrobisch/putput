'use strict';

var fs = require("fs");
var angular = require('angular');
var NavbarController = require("./NavbarController");

var navbarModule = angular.module('navbar', []);

navbarModule.controller("NavbarController", NavbarController);

module.exports = navbarModule;
