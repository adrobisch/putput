'use strict';
var fs = require("fs");

var angular = require('angular');

var app = angular.module('app', [
  require("angular-route").name,
  require("angular-animate").name,
  require("ng-flow").name,
  require("angular-loading-bar").name,
  require('./login/LoginModule').name,
  require('./home/HomeModule').name,
  require('./navbar/NavbarModule').name,
  require('./contacts/ContactsModule').name,
  require('./user/SettingsModule').name,
  require('./user/PasswordResetModule').name
]);

app.service('api', require('./common/ApiService'));
app.service('auth', require('./common/AuthenticationApi'));
app.service('contacts', require('./contacts/ContactsApi'));
app.service('users', require('./user/UsersApi'));
app.service('$alert', require('./common/AlertService'));

app.factory('authInterceptor', require('./common/AuthInterceptor'));

app.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.interceptors.push('authInterceptor');
}]);

app.config(['flowFactoryProvider', function (flowFactoryProvider) {
  flowFactoryProvider.defaults = {
    target: '/api/upload',
    uploadMethod: 'POST',
    permanentErrors: [401, 500, 501],
    maxChunkRetries: 0,
    chunkRetryInterval: 5000,
    simultaneousUploads: 4,
    method: 'octet'
  };
  flowFactoryProvider.on('catchAll', function (event) {
    console.log('catchAll', arguments);
  });
}]);

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({
    redirectTo: '/'
  });
}]);

module.exports = app;
