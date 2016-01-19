var ItemViewController = function (scope, route) {
    scope.itemId = route.current.params["itemId"];
};

ItemViewController.$inject = ["$scope", "$route"];
module.exports = ItemViewController;