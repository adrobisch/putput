function FilesApi(http, api) {

  this.getFile = function(fileId) {
    return api.withLink("file", function (fileLink) {
      return http.get(fileLink.replace("{id}", fileId));
    });
  };

  this.getTags = function(file) {
    return http.get(file._links.tags.href);
  };

  this.createTag = function (file, text) {
    return http.post(file._links.tags.href, {
      "name" : "label",
      "text" : text
    });
  };

  this.deleteTag = function (tag) {
    return http.delete(tag._links.self.href);
  };

  this.list = function(parent, tag) {
    return api.withLink("files", function (filesLink){
      var url = filesLink + "?" +
          (parent ? "parent=" + parent : "") +
          "&" +
          (tag ? "tag=" + tag : "");

      return http.get(url);
    });
  };
}

FilesApi.$inject = ["$http", "api"];

module.exports = FilesApi;