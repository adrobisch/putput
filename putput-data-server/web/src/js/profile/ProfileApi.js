function ProfileApi(http) {
    this.getProfile = function(userId) {
        return http.get("/api/profile/" + userId);
    };

    this.follow = function(profile) {
        return http.post(profile._links.follow.href);
    };

    this.unfollow = function(profile) {
        return http.delete(profile._links.follow.href);
    };
}

ProfileApi.$inject = ["$http"];

module.exports = ProfileApi;