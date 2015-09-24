var FileListController =  function(scope, files) {

  scope.fileUploaded = function () {
    scope.loadFiles();
  };

  scope.loadFiles = function () {
    files.list().success(function (result) {
      scope.files = result.data.files;
    });
  };
  
  scope.download = function (file) {
    window.location.href = scope.downloadUrl(file);
  };
  
  scope.downloadUrl = function (file) {
    return file._links["content"].href;
  };

};

FileListController.$inject = ['$scope', 'files'];

module.exports = FileListController;