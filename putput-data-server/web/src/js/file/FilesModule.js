'use strict';

var angular = require('angular');
var FileListController = require("./FileListController");

var filesModule = angular.module('files', []);
filesModule.service("files", require('./FilesApi'));

filesModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/files/:filePath*?', {
    controller: FileListController,
    template: require('./file-list.html')
  });
}]);

module.exports = filesModule;
