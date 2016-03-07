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
        calendar.getEvent(date.serverId).success(function (response) {
            scope.currentEvent = response.data;
        });
    };

    scope.createEvent = function (event) {
        if (!event.title || !event.startDate || !event.endDate) {
            return;
        }
        
        var type = event.allDay ? "ALLDAY" : "DEFAULT";

        var newEvent = {
            "start": event.startDate.getTime(),
            "end": event.endDate.getTime(),
            "type": type,
            "title": event.title,
            "location": event.location,
            "description": event.description
        };
        
        calendar.createEvent(newEvent).success(scope.reloadEvents);
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