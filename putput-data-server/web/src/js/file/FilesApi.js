function FilesApi(http, api) {
  this.list = function(path) {
    return api.withLink("files", function (filesLink){
      var url = filesLink + (path ? "?path=" + path : "");
        return http.get(url);
    });
  };
}

FilesApi.$inject = ["$http", "api"];

module.exports = FilesApi;