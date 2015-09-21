var FileListController =  function(scope, files) {

  scope.loadFiles = function () {
    files.list().success(function (files) {
      scope.files = files.data;
    });
  };

};

FileListController.$inject = ['$scope', 'files'];

module.exports = FileListController;