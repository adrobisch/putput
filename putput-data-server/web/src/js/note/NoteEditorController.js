var asciidoctor = require('asciidoctor');

var NoteEditorController =  function(scope, routeParams, notes, alert) {
  scope.getNote = function () {
    notes.get(routeParams.id).success(function (response) {
      scope.note = response.data;
    });
  };

  scope.updateNote = function () {
    notes.update(scope.note).success(function (){
      alert.info("Update", "Successful!");
    });
  };

  scope.init = scope.getNote;
};

NoteEditorController.$inject = ['$scope', '$routeParams', 'notes', '$alert'];

module.exports = NoteEditorController;