'use strict';

var angular = require('angular');
var NotesController = require("./NotesController");
var NoteEditorController = require("./NoteEditorController");
var NoteViewController = require("./NoteViewController");
var notesModule = angular.module('notes', []);

notesModule.service("notes", require('./NotesApi'));
notesModule.service("markdown", require('./MarkdownService'));

notesModule.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/notes', {
    controller: NotesController,
    template: require('./notes.html')
  }).when('/note/:id', {
    controller: NoteEditorController,
    template: require('./note_editor.html')
  }).when('/note/view/:id', {
    controller: NoteViewController,
    template: require('./note_view.html')
  });
}]);

module.exports = notesModule;
