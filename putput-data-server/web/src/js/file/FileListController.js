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
      var newPath = "/files/" + (scope.path ? scope.path + "/" : "") + file.name;
      location.path(newPath);  
    } else {
      window.location.href = scope.downloadUrl(file);  
    }
  };
  
  scope.downloadUrl = function (file) {
    return file._links["content"].href;
  };

};

FileListController.$inject = ['$scope', '$route', '$location', 'files'];

module.exports = FileListController;