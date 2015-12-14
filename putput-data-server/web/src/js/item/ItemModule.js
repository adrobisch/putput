'use strict';

var angular = require('angular');
var ItemController = require("./ItemController");

var itemModule = angular.module('item', []);

itemModule.controller("ItemController", ItemController);

itemModule.config(['$routeProvider', function($routeProvider) {

  $routeProvider.when('/item/:itemId', {
    controller: ItemController,
    template: require('./item.html')
  });

}]);

itemModule.directive("streamItem", function () {
    return {
        controller: ItemController,
        scope: {
            "item" : "=item"
        },
        template: require('./itemTemplate.html')
    };
});

module.exports = itemModule;
