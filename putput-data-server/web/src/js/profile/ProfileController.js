var profileController = function (scope, profile, route) {
    scope.userId = route.current.params["userId"];
    scope.profile = null;

    scope.getProfile = function () {
        profile.getProfile(scope.userId).success(function (data) {
            scope.resource = data;
            scope.profile = scope.resource.profile;
            scope.following = scope.resource.following;
        });
    };

    scope.follow = function () {
        profile.follow(scope.resource).success(scope.reload);
    };

    scope.unfollow = function () {
        profile.unfollow(scope.resource).success(scope.reload);
    };

    scope.reload = function () {
        scope.getProfile();
    };

    scope.getProfile();
};

profileController.$inject = ["$scope", "profile", "$route"];
module.exports = profileController;