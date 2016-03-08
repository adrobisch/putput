function EventFormController(scope) {
    scope.event = scope.event || undefined;

    scope.$watch("event", function (newValue, oldValue) {
        if (newValue && typeof(newValue.start) === 'number') {
            newValue.start = new Date(newValue.start);
        }

        if (newValue && typeof(newValue.end) === 'number') {
            newValue.end = new Date(newValue.end);
        }
    });

    scope.showStartDate = false;
    scope.showEndDate = false;
    
    scope.submit = function () {
      if (scope.onSubmit) {
          var type = scope.event.allDay ? "ALLDAY" : "DEFAULT";

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
}

EventFormController.$inject = ["$scope"];

module.exports = EventFormController;