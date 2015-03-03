var NotesController =  function(scope, notes, location) {
  scope.newNote = {};
  scope.notesList = {};

  scope.getNotes = function () {
    notes.list().success(function (notesResponse) {
      scope.notesList = notesResponse.data;
    });
  };

  scope.createNote = function () {
    notes.create(scope.newNote).success(function (resonse) {
      var location = resonse.headers("Location");
      notes.getByUri(location, function (note) {
        scope.edit(note.data.id);
      });
    });
  };

  scope.edit = function(noteId) {
    location.path("/note/" + noteId);
  };

  scope.init = scope.getNotes;
};

NotesController.$inject = ['$scope', 'notes', '$location'];

module.exports = NotesController;