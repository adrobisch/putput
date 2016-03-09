 var NavbarController = function (scope, location, hotkeys) {
    scope.currentLocation = "";

    scope.$on('$routeChangeStart', function(next, current) {
        scope.currentLocation = location.path();
    });

    scope.goto = function (newLocation) {
        location.path(newLocation);
        location.url(location.path());
    };

    scope.activeClass = function (locationName) {
        return scope.currentLocation.indexOf(locationName) != -1 ? ['active'] : [];
    };

    hotkeys.add({
        description: "inbox",
        combo: 'alt+i',
        callback: function () {
            scope.goto("/inbox");
        }
    });
     
    hotkeys.add({
        description: "home",
        combo: 'alt+h',
        callback: function () {
            scope.goto("/home");
        }
    });
};
NavbarController.$inject = ["$scope", "$location", "hotkeys"];

module.exports = NavbarController;