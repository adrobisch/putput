var _ = require("lodash");

var HomeController = function (scope, timeline, rootScope, hotkeys, users) {
    scope.stream = [];
    scope.imageUrl = null;
    scope.filter = "all";
    scope.newPut = null;

    scope.users = [];
    scope.emojis = [];

    scope.setFilter = function(filter) {
        scope.filter = filter;
    };
};
HomeController.$inject = ["$scope", "timeline", "$rootScope", "hotkeys", "users"];

module.exports = HomeController;