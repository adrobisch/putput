var _ = require("lodash");

var InboxController = function (scope, calendar, hotkeys, uiCalendarConfig) {
    scope.calendar = {
        height: 450,
        editable: false,
        header: {
            left: 'month,agendaWeek,agendaDay',
            center: 'title',
            right: 'today prev,next'
        },
        eventClick: function(date, jsEvent, view){
            scope.onEventClick(date);
        }
    };

    scope.events = {};
    scope.calendarEvents = [];
    scope.showCreateForm = false;
    scope.currentEvent = null;
    
    scope.init = function () {
    };
    
    scope.getEvents = function (start, end, timezone, callback) {
        return calendar.getEvents().success(function (response) {
            scope.events = response.data.events;
            scope.calendarEvents = [];
            _.forEach(scope.events, function (event) {
                scope.calendarEvents.push(scope.serverEventToCalendarEvent(event));
            });
            if (callback) {
                callback(scope.calendarEvents);
            }
        });
    };
    
    scope.serverEventToCalendarEvent = function (serverEvent) {
        return {
            "serverId": serverEvent.id,
            "title": serverEvent.title,
            "start": new Date(serverEvent.start),
            "end": new Date(serverEvent.end),
            "allDay": serverEvent.type === "ALLDAY"
        };
    };

    scope.loadNext = function () {
        calendar.nextPage(scope.events).success(function (events) {
            scope.events = events;
        });
    };

    scope.loadPrevious = function () {
        calendar.previousPage(scope.events).success(function (events) {
            scope.events = events;
        });
    };
    
    scope.onEventClick = function(date){
        scope.editEvent(date.serverId);
    };

    scope.editEvent = function (id) {
        calendar.getEvent(id).success(function (response) {
            scope.currentEvent = response.data;
        });
    };

    scope.createEvent = function (event) {
        calendar.createEvent(event).success(function () {
            scope.showCreateForm = false;
            scope.reloadEvents();
        });
    };

    scope.updateEvent = function (event) {
        calendar.updateEvent(event).success(function() {
            scope.currentEvent = null;
            scope.reloadEvents();
        });
    };

    scope.deleteEvent = function (event) {
        calendar.deleteEvent(event).success(scope.reloadEvents);
    };

    scope.reloadEvents = function () {
        uiCalendarConfig.calendars["calendarInstance"].fullCalendar('refetchEvents');
    };

    scope.eventSources = [scope.getEvents];
};

InboxController.$inject = ["$scope", "calendar", "hotkeys", "uiCalendarConfig"];

module.exports = InboxController;