var InboxController = function (scope, calendar, hotkeys) {
    scope.newEvent = {
    };
    
    scope.calendar = {
        height: 450,
        editable: true,
        header: {
            left: 'month basicWeek basicDay agendaWeek agendaDay',
            center: 'title',
            right: 'today prev,next'
        }
    };
    
    scope.eventSources = {
        events: [
            {
                title: 'Event1',
                start: '2016-01-25 12:00',
                allDay: false
            },
            {
                title: 'Event2',
                start: '2016-01-25'
            }
        ],
        color: 'yellow',
        textColor: 'black'
    };
    
    scope.events = {};
    
    scope.init = function () {
      scope.getEvents();  
    };
    
    scope.getEvents = function () {
        calendar.getEvents().success(function (response) {
            scope.events = response.data;
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
        if (!scope.newEvent) {
            return;
        }
        
        messages.sendMessage(scope.newEvent.message).success(function () {
            scope.getMessages();
            scope.compose = false;
        });
    };
};

InboxController.$inject = ["$scope", "messages", "hotkeys"];

module.exports = InboxController;