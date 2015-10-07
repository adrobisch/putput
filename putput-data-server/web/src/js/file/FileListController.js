var _ = require("lodash");

var FileListController =  function(scope, route, location, files, search) {
  scope.flow = {};
  scope.tag = null;
  scope.playing = false;
  
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

  scope.startSlideShow = function () {
    jQuery(".file-gallery").swipeshow();
    scope.playing = true;
  };

  scope.isImage = function (file) {
    return file.mimeType.indexOf('image') != -1;
  };

  scope.search = function (query) {
    scope.tag = query;
    scope.loadFiles();
    console.log("file search: " + query);
  };

  search.onSearch(scope.search, scope);
};

FileListController.$inject = ['$scope', '$route', '$location', 'files', 'search'];

module.exports = FileListController;