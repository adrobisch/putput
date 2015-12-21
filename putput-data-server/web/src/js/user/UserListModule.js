var angular = require("angular");

var UserlistModule = angular.module('userlist', [
    require("./UsersModule").name
]);

var userlistController = function UserlistController(scope, users, rootScope) {
    scope.userPage = {};

    users.getUsers(function (userPage) {
        scope.userPage = userPage;
    });
    
    scope.loadNextPage = function () {
        users.getUsersFromUrl(function (userPage) {
            scope.userPage._links = userPage._links;
            scope.userPage.page = userPage.page;
            scope.userPage.users = scope.userPage.users.concat(userPage.users);
        })(scope.userPage._links.nextPage.href);
    };
};
userlistController.$inject = ["$scope", "users", "$rootScope"];

UserlistModule.directive("userlist", function () {
    return {
        controller: userlistController,
        scope: {
        },
        template: require('./user_list.html')
    };
});

module.exports = UserlistModule;