'use strict';

var fs = require("fs");
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
    template: fs.readFileSync(__dirname + '/notes.html', 'utf-8')
  }).when('/note/:id', {
    controller: NoteEditorController,
    template: fs.readFileSync(__dirname + '/note_editor.html', 'utf-8')
  }).when('/note/view/:id', {
    controller: NoteViewController,
    template: fs.readFileSync(__dirname + '/note_view.html', 'utf-8')
  });
}]);

module.exports = notesModule;
