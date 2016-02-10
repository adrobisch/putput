var _ = require("lodash");

var InboxController = function (scope, calendar, hotkeys) {
    scope.calendar = {
        height: 450,
        editable: true,
        header: {
            left: 'month basicWeek basicDay agendaWeek agendaDay',
            center: 'title',
            right: 'today prev,next'
        }
    };
    
    scope.events = {};
    scope.calendarEvents = [];
    
    scope.init = function () {
      scope.getEvents();  
    };
    
    scope.getEvents = function () {
        calendar.getEvents().success(function (response) {
            scope.events = response.data.events;
            _.forEach(scope.events, function (event) {
               scope.calendarEvents.push({
                   "title": event.title,
                   "start": new Date(event.start),
                   "allDay": event.type === "ALLDAY" 
               }) 
            });
        });
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

        calendar.createEvent({
            "start": scope.startDate.getTime(),
            "end": scope.endDate.getTime(),
            "type": type,
            "title": scope.title
        }).success(function () {
            scope.getEvents();
        });
    };

    scope.eventSources = [scope.calendarEvents];
};

InboxController.$inject = ["$scope", "calendar", "hotkeys"];

module.exports = InboxController;