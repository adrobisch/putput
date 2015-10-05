var _ = require("lodash");

var FileViewController =  function(scope, route, location, files) {
  scope.fileId = route.current.params.fileId;

  scope.file = {};

  scope.loadFile = function () {
    files.getFile(scope.fileId).success(function (response) {
      scope.file = response.data;
    });
  };

  scope.isImage = function () {
    if (!scope.file.mimeType) {
      return false;
    }
    return scope.file.mimeType.indexOf("image") != -1;
  }

  scope.download = function () {
    window.location.href = scope.contentUrl();
  };

  scope.contentUrl = function () {
    if (scope.file._links) {
      return scope.file._links["content"].href;
    }
  };
};

FileViewController.$inject = ['$scope', '$route', '$location', 'files'];

module.exports = FileViewController;