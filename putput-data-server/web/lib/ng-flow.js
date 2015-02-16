'use strict';

var angular = require('angular');
var flow = require('../bower_components/flow.js/dist/flow');
window.Flow = flow;

require('../bower_components/ng-flow/dist/ng-flow');

module.exports = angular.module("flow");