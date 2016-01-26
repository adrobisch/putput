function ProfileApi(api, http) {
    this.getProfile = function(userId) {
        return api.withLink("profile", function (profileLink) {
            return http.get(profileLink.replace("{userName}", userId));
        });
    };

    this.follow = function(profile) {
        return http.post(profile._links.follow.href);
    };

    this.unfollow = function(profile) {
        return http.delete(profile._links.follow.href);
    };
}

ProfileApi.$inject = ["api", "$http"];

module.exports = ProfileApi;