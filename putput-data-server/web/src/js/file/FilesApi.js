function FilesApi(http, api) {
  this.list = function() {
    return api.withLink("files", function (filesLink){
      return http.get(filesLink);
    });
  };
}

FilesApi.$inject = ["$http", "api"];

module.exports = FilesApi;