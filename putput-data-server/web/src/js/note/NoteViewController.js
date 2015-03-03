var NoteViewController =  function(scope, routeParams, notes, markdown) {
  scope.getNote = function () {
    notes.get(routeParams.id).success(function (response) {
      scope.renderedNote = markdown.render(response.data.content);
    });
  };

  scope.init = scope.getNote;
};

NoteViewController.$inject = ['$scope', '$routeParams', 'notes', 'markdown'];

module.exports = NoteViewController;