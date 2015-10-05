function FilesApi(http, api) {

  this.getFile = function(fileId) {
    return  api.withLink("file", function (fileLink) {
      return http.get(fileLink.replace("{id}", fileId));
    });
  };

  this.list = function(parent) {
    return api.withLink("files", function (filesLink){
      var url = filesLink + (parent ? "?parent=" + parent : "");
        return http.get(url);
    });
  };
}

FilesApi.$inject = ["$http", "api"];

module.exports = FilesApi;