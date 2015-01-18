'use strict';

var angular = require('angular');

var itemModule = angular.module('item', []);

itemModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/item/:itemId', {
    controller: require("./ItemViewController"),
    template: require('./item-view.html')
  });
}]);

itemModule.directive("itemInput", require("./ItemInputDirective"));
itemModule.directive("streamItem", require("./ItemDirective"));

itemModule.run(['$templateCache', function (templateCache) {
    templateCache.put("user-mentions.tpl", require("./user-mentions.html"));
    templateCache.put("emoji.tpl", require("./emoji-list.html"));
}]);

module.exports = itemModule;
