var FileListController =  function(scope, files) {

  scope.fileUploaded = function (file) {
    scope.loadFiles();
  };

  scope.loadFiles = function () {
    files.list().success(function (result) {
      scope.files = result.data.files;
    });
  };

};

FileListController.$inject = ['$scope', 'files'];

module.exports = FileListController;