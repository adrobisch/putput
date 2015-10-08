function MediaService(rootScope) {
    var mediaAddedKey = "media.added";
    
    this.medias = [];
    
    this.getMedias = function () {
        return this.medias;
    };
    
    this.setMedias = function (medias) {
      this.medias = medias;  
    };
    
    this.addMedia = function (media) {
        if (media) {
            this.media.push(media);
            rootScope.$emit(mediaAddedKey, {"media": media});    
        }
    };

    this.onMediaAdded = function (mediaHandler, scope) {
        var listener = function (event, payload) {
            mediaHandler(payload.media);
        };

        var listenerRegistration = rootScope.$on(mediaAddedKey, listener);

        if (scope) {
            scope.$on('$destroy', function() {
                listenerRegistration();
            });
        }

        return listenerRegistration;
    };
}

MediaService.$inject = ["$rootScope"];

module.exports = MediaService;