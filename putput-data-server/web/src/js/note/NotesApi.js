function NotesApi(http, api) {
  this.create = function(newNote) {
    return api.withLink("note", function (noteLink){
      return http.post(noteLink.replace("{id}", ''), newNote);
    });
  };

  this.get = function (id) {
    return api.withLink("note", function (noteLink) {
      return http.get(noteLink.replace("{id}", id));
    });
  };

  this.getByUri = function (uri, callback) {
    http.get(uri).then(callback);
  };

  this.list = function() {
    return api.withLink("notes", function (notesLink){
      return http.get(notesLink);
    });
  };

  this.update = function (note) {
    console.log("updating", note);
    return http.put(note["_links"]["self"].href, note);
  };

  this.delete = function(note) {
    console.log("deleting", note);
    return http.delete(note["_links"]["self"].href);
  }
}

NotesApi.$inject = ["$http", "api"];

module.exports = NotesApi;