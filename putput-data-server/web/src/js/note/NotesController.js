var NotesController =  function(scope, notes, location, shortcut, alerts) {
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
    }).error(function (e) {
      console.log(e);
      alerts.error("Error", "Unable to create note!")
    });
  };

  scope.edit = function(noteId) {
    location.path("/note/" + noteId);
  };

  scope.init = function () {
    shortcut.add('ctrl+s', scope.createNote, scope);
    scope.getNotes();
  };
};

NotesController.$inject = ['$scope', 'notes', '$location', 'shortcut', '$alert'];

module.exports = NotesController;