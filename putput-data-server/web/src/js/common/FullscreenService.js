var FullscreenService = function () {
    return {
        toggleFullScreen: function toggleFullScreen(element) {
            if (element.requestFullscreen) {
                element.requestFullscreen();
            } else if (element.msRequestFullscreen) {
                element.msRequestFullscreen();
            } else if (element.mozRequestFullScreen) {
                element.mozRequestFullScreen();
            } else if (element.webkitRequestFullscreen) {
                element.webkitRequestFullscreen();
            }
        }
    };
};

FullscreenService.$inject = [];

module.exports = FullscreenService;

