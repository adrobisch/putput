var YoutubeController = function (scope, sce) {
    scope.embeddedUrl = function () {
        return sce.trustAsResourceUrl("//www.youtube.com/embed/"+scope.youtubeId);
    }
};

YoutubeController.$inject = ["$scope", "$sce"];

module.exports = function() {
    return {
        template: require("./youtube.html"),
        controller: YoutubeController,
        scope : {
            youtubeId : "="
        }
    };
};