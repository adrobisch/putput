'use strict';

var angular = require('angular');
var FileListController = require("./FileListController");
var FileViewController = require("./FileViewController");

var filesModule = angular.module('files', []);
filesModule.service("files", require('./FilesApi'));

filesModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/files/:parent*?', {
    controller: FileListController,
    template: require('./file-list.html')
  }).when('/file/:fileId', {
    controller: FileViewController,
    template: require('./file-view.html')
  })
}]);

filesModule.filter('bytes', function() {
  return function(bytes, precision) {
    if (isNaN(parseFloat(bytes)) || !isFinite(bytes)) return '-';
    if (typeof precision === 'undefined') precision = 1;
    if (bytes === 0) return '0 bytes';
    var units = ['bytes', 'kB', 'MB', 'GB', 'TB', 'PB'],
        number = Math.floor(Math.log(bytes) / Math.log(1024));
    return (bytes / Math.pow(1024, Math.floor(number))).toFixed(precision) +  ' ' + units[number];
  }
});

module.exports = filesModule;
