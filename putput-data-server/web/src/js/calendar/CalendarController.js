var _ = require("lodash");

var InboxController = function (scope, calendar, hotkeys) {
    scope.calendar = {
        height: 450,
        editable: true,
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
        calendar.getEvents().success(function (response) {
            scope.events = response.data.events;
            scope.calendarEvents = [];
            _.forEach(scope.events, function (event) {
                scope.calendarEvents.push(scope.serverEventToCalendarEvent(event));
            });
            callback(scope.calendarEvents);
        });
    };
    
    scope.serverEventToCalendarEvent = function (serverEvent) {
        return {
            "title": serverEvent.title,
            "start": new Date(serverEvent.start),
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
        if (!scope.title && !scope.startDate && !scope.endDate) {
            return;
        }
        
        if (scope.startTime) {
            scope.startDate.setHours(scope.startTime.getHours());
            scope.startDate.setMinutes(scope.startTime.getMinutes());
        }
        
        if (scope.endTime) {
            scope.endDate.setHours(scope.endTime.getHours());
            scope.endDate.setMinutes(scope.endTime.getMinutes());
        }
        
        var type = scope.allDay ? "ALLDAY" : "DEFAULT";

        var newEvent = {
            "start": scope.startDate.getTime(),
            "end": scope.endDate.getTime(),
            "type": type,
            "title": scope.title
        };
        
        calendar.createEvent(newEvent).success(function () {
            scope.calendarEvents.push(newEvent);
        });
    };

    scope.eventSources = [scope.getEvents];
};

InboxController.$inject = ["$scope", "calendar", "hotkeys"];

module.exports = InboxController;