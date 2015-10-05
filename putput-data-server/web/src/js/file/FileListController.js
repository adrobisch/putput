var _ = require("lodash");

var FileListController =  function(scope, route, location, files) {
  scope.flow = {};
  
  scope.path = route.current.params.filePath;
  
  scope.upload = function () {
    _.each(scope.flow.instance.files, function (file) {
      file.relativePath = scope.path ? scope.path : "/";
    });
    
    scope.flow.instance.upload();
  };
  
  scope.fileUploaded = function () {
    scope.loadFiles();
  };

  scope.loadFiles = function () {
    files.list(scope.path).success(function (result) {
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

};

FileListController.$inject = ['$scope', '$route', '$location', 'files'];

module.exports = FileListController;