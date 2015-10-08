var MediaActionController = function (scope, media, location) {
    scope.$watchCollection('medias', function(newValue, oldValue) {
        if (newValue) {
            media.setMedias(newValue);
        }
    });

    scope.start = function () {
        location.path("/media/slideshow");
    };
};

MediaActionController.$inject = ["$scope", "media", "$location"];

module.exports = function() {
    return {
        template: require("./media-action.html"),
        controller: MediaActionController,
        scope : {
            action : "@action",
            medias : "=medias"
        }
    };
};