var _ = require("lodash");

var InboxController = function (scope, calendar, hotkeys, uiCalendarConfig) {
    scope.calendar = {
        height: 450,
        editable: false,
        header: {
            left: 'month,agendaWeek,agendaDay',
            center: 'title',
            right: 'today prev,next'
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

    scope.createEvent = function () {
        if (!scope.title || !scope.startDate || !scope.endDate) {
            return;
        }
        
        var type = scope.allDay ? "ALLDAY" : "DEFAULT";

        var newEvent = {
            "start": scope.startDate.getTime(),
            "end": scope.endDate.getTime(),
            "type": type,
            "title": scope.title
        };
        
        calendar.createEvent(newEvent).success(scope.reloadEvents);
    };

    scope.onStartDate = function () {
        if (!scope.endDate || (scope.endDate.getTime()) < scope.startDate.getTime() ) {
            scope.endDate = new Date(scope.startDate.getTime());
        }
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