'use strict';

var angular = require('angular');
var SettingsController = require("./SettingsController");

var settingsModule = angular.module('user-settings', []);

settingsModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/settings', {
    controller: SettingsController,
    template: require('./user_settings.html')
  });
}]);

settingsModule.directive('equals', function() {
  return {
    restrict: 'A', // only activate on element attribute
    require: '?ngModel', // get a hold of NgModelController
    link: function(scope, elem, attrs, ngModel) {
      if(!ngModel) return; // do nothing if no ng-model

      // watch own value and re-validate on change
      scope.$watch(attrs.ngModel, function() {
        validate();
      });

      // observe the other value and re-validate on change
      attrs.$observe('equals', function (val) {
        validate();
      });

      var validate = function() {
        // values
        var val1 = ngModel.$viewValue || '';
        var val2 = attrs.equals;

        // set validity
        ngModel.$setValidity('equals', val1 === val2);
      };
    }
  }
});

module.exports = settingsModule;
