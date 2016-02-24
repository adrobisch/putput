var _ = require("lodash");

var FileListController =  function(scope, route, location, files, search, media) {
  scope.flow = {};
  scope.flowConfig = {
    query: {
      "type" : "file"
    }
  };
  scope.tag = null;
  scope.playing = false;
  scope.files = [];
  
  scope.parent = route.current.params.parent;
  
  scope.upload = function () {
    _.each(scope.flow.instance.files, function (file) {
      file.relativePath = scope.parent ? scope.parent : "/";
    });
    
    scope.flow.instance.upload();
  };
  
  scope.fileUploaded = function () {
    scope.loadFiles();
  };

  scope.loadFiles = function () {
    scope.playing = false;
    files.list(scope.parent, scope.tag).success(function (result) {
      scope.files = result.data.files;
    });
  };
  
  scope.open = function (file) {
    if (file.isDirectory) {
      location.path("/files/" + file.id);
    } else {
      location.path("/file/" + file.id);
    }
  };
  
  scope.downloadUrl = function (file) {
    return file._links["content"].href;
  };

  scope.play = function () {
    
  };

  scope.search = function (query) {
    scope.tag = query;
    scope.loadFiles();
    console.log("file search: " + query);
  };

  search.onSearch(scope.search, scope);

  search.setEnabled(true);

  scope.$on("$destroy", function() {
    search.setEnabled(false);
  });
};

FileListController.$inject = ['$scope', '$route', '$location', 'files', 'search', 'media'];

module.exports = FileListController;