function addDays(date, days) {
    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}

function EventFormController(scope) {
    scope.event = scope.event || undefined;

    scope.$watch("event", function (newValue, oldValue) {
        if (newValue && typeof(newValue.start) === 'number') {
            newValue.start = new Date(newValue.start);
        }

        if (newValue && typeof(newValue.end) === 'number') {
            newValue.end = new Date(newValue.end);
        }

        if (newValue && newValue.type === 'ALLDAY') {
            newValue.allDay = true;
            newValue.end = addDays(newValue.end, -1);
        }
    });

    scope.showStartDate = false;
    scope.showEndDate = false;
    
    scope.submit = function () {
      if (scope.onSubmit) {
          var type = scope.event.allDay ? "ALLDAY" : "DEFAULT";

          scope.event.allDay = null;
          scope.event.type = type;
          scope.event.start = scope.event.start.getTime();
          scope.event.end = scope.event.end.getTime();

          scope.onSubmit(scope.event);
          scope.event = {};
      }
    };

    scope.onDateTimeChange = function () {
        if (!scope.event.end || (scope.event.end.getTime()) < scope.event.start.getTime() ) {
            scope.event.end = new Date(scope.event.start.getTime());
        }
    };

    scope.validateForm = function() {
        if (scope.event && 
            scope.event.title && 
            scope.event.start && 
            scope.event.end) {
            return true;
        }
        return false;
    };
}

EventFormController.$inject = ["$scope"];

module.exports = EventFormController;