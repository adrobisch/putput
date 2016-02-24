'use strict';

var angular = require('angular');
var SlideshowController = require("./SlideshowController");

var mediaModule = angular.module('media', []);

mediaModule.service("media", require("./MediaService"));

mediaModule.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/media/slideshow', {
        controller: SlideshowController,
        template: require('./slideshow.html')
    });
}]);

mediaModule.directive("mediaAction", require("./MediaActionDirective"));
mediaModule.directive("youtube", require("./YoutubeDirective"));

mediaModule.controller("SlideshowController", SlideshowController);

require("./ngSlideShow")(mediaModule);

module.exports = mediaModule;
