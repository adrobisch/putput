var HomeController = function (scope, timeline, rootScope, hotkeys) {
    scope.stream = [];
    scope.imageUrl = null;
    scope.filter = "all";
    scope.newPut = null;

    scope.setFilter = function(filter) {
        scope.filter = filter;
    };

    scope.put = function () {
        timeline.postItem(scope.newPut).success(function() {
            rootScope.$emit("item.created");
            scope.newPut = "";
        })
    };

    hotkeys.add({
        combo: 'ctrl+return',
        allowIn: ['TEXTAREA'],
        callback: scope.put
    });

    hotkeys.add({
        combo: 'shift+return',
        allowIn: ['TEXTAREA'],
        callback: scope.put
    });
};
HomeController.$inject = ["$scope", "timeline", "$rootScope", "hotkeys"];

module.exports = HomeController;