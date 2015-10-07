'use strict';

var jQuery = window.jQuery = require("jquery");
require("swipeshow");
var flow = window.Flow = require("flow.js/dist/flow");
var angular = window.angular = require('angular');

require("ng-flow/dist/ng-flow");
require('angular-hotkeys');
require('bootstrap');

var app = angular.module('app', [
  "cfp.hotkeys",
  "flow",
  require("angular-route"),
  require("angular-animate"),
  require("angular-loading-bar"),
  require('./login/LoginModule').name,
  require('./home/HomeModule').name,
  require('./navbar/NavbarModule').name,
  require('./contacts/ContactsModule').name,
  require('./note/NotesModule').name,
  require('./user/UsersModule').name,
  require('./user/SettingsModule').name,
  require('./user/PasswordResetModule').name,
  require('./file/FilesModule').name,
  require("./search/SearchModule").name
]);

app.service('api', require('./common/ApiService'));
app.service('auth', require('./common/AuthenticationApi'));
app.service('$alert', require('./common/AlertService'));
app.service('shortcut', require('./common/ShortcutService'));
app.service('fullscreen', require('./common/FullscreenService'));

app.factory('authInterceptor', require('./common/AuthInterceptor'));

app.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.interceptors.push('authInterceptor');
}]);

app.config(['flowFactoryProvider', function (flowFactoryProvider) {
  flowFactoryProvider.defaults = {
    target: '/api/upload',
    chunkSize: 131072,
    uploadMethod: 'POST',
    permanentErrors: [401, 500, 501],
    maxChunkRetries: 0,
    chunkRetryInterval: 5000,
    simultaneousUploads: 3,
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

app.directive('ngConfirmClick', [
  function(){
    return {
      priority: -1,
      restrict: 'A',
      link: function(scope, element, attrs){
        element.bind('click', function(e){
          var message = attrs.ngConfirmClick;
          if(message && !confirm(message)){
            e.stopImmediatePropagation();
            e.preventDefault();
          }
        });
      }
    }
  }
]);

module.exports = app;
