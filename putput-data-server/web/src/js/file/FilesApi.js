function FilesApi(http, api) {
  this.list = function(parent) {
    return api.withLink("files", function (filesLink){
      var url = filesLink + (parent ? "?parent=" + parent : "");
        return http.get(url);
    });
  };
}

FilesApi.$inject = ["$http", "api"];

module.exports = FilesApi;