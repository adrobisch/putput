var NoteEditorController =  function(scope, routeParams, notes, alert, location, markdown, shortcut) {
  scope.previewToggle = true;

  scope.getNote = function () {
    notes.get(routeParams.id).success(function (response) {
      scope.note = response.data;
    });
  };

  scope.updateNote = function () {
    return notes.update(scope.note).success(function (){
      alert.info("Note", "Updated successfully!", 1000);
    });
  };

  scope.preview = function () {
    if (scope.note) {
      return markdown.render(scope.note.content);
    }
  };

  scope.viewNote = function () {
    scope.updateNote().success(function () {
      location.path("/note/view/" + scope.note.id);
    });
  };

  scope.deleteNote = function () {
    notes.delete(scope.note).success(function () {
        location.path("/notes");
    });
  };

  scope.init = function () {
    shortcut.add('ctrl+s', scope.updateNote, scope);

    scope.getNote();
  };
};

NoteEditorController.$inject = ['$scope', '$routeParams', 'notes', '$alert', '$location', 'markdown', 'shortcut'];

module.exports = NoteEditorController;