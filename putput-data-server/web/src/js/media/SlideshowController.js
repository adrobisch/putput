var SlideshowController = function (scope, media, fullscreen) {
    scope.medias = [];
    
    scope.isImage = function (media) {
        return media.mimeType.indexOf('image') != -1;
    };

    scope.start = function () {
    };
    
    scope.init = function () {
      scope.medias = media.getMedias();
    };
    
    scope.init();
};

SlideshowController.$inject = ["$scope", "media", "fullscreen"];

module.exports = SlideshowController;