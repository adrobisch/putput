var ngSanitize = require("angular-sanitize");
var ngInifinteScroll = require("ng-infinite-scroll");
var itemModule = require("../item/ItemModule");

var TimelineController = require("./TimelineController");
var TimelineModule = angular.module('timeline', [ngSanitize, 'infinite-scroll', itemModule.name]);

TimelineModule.service("timeline", require("./TimelineApi"));

TimelineModule.directive("timeline", function () {
    return {
        controller: TimelineController,
        scope: {
          "profile" : "=profile",
          "filter" : "=filter"
        },
        template: require('./timeline.html')
    };
});

TimelineModule.filter('breakFilter', function () {
    return function (text) {
        console.log("text", text);
        if (typeof text.valueOf === "function") {
            return text.valueOf().substring(0, 180);
        } else if(typeof text === "string") {
            return text.substring(0, 180);
        } else return text;
    };
});

module.exports = TimelineModule;