var _ = require("lodash");

var HomeController = function (scope, timeline, rootScope, hotkeys) {
    scope.stream = [];
    scope.imageUrl = null;
    scope.filter = "all";
    scope.newPut = null;

    scope.users = [];
    scope.emojis = [];

    scope.setFilter = function(filter) {
        scope.filter = filter;
    };

    scope.put = function () {
        if (!scope.newPut) {
            return;
        }

        timeline.postItem(scope.newPut).success(function() {
            rootScope.$emit("item.created");
            scope.newPut = "";
        });
    };

    hotkeys.bindTo(scope).add({
        description: "send put",
        combo: 'ctrl+return',
        allowIn: ['TEXTAREA'],
        callback: scope.put
    });

    hotkeys.bindTo(scope).add({
        description: "send put",
        combo: 'shift+return',
        allowIn: ['TEXTAREA'],
        callback: scope.put
    });
};

HomeController.$inject = ["$scope", "timeline", "$rootScope", "hotkeys"];

module.exports = HomeController;