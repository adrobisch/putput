function EventFormController(scope) {
    scope.event = scope.event || {};
    
    scope.showStartDate = false;
    scope.showEndDate = false;
    
    scope.submit = function () {
      if (scope.onSubmit) {
          scope.onSubmit(scope.event);
      }
    };

    scope.onDateTimeChange = function () {
        if (!scope.event.endDate || (scope.event.endDate.getTime()) < scope.event.startDate.getTime() ) {
            scope.event.endDate = new Date(scope.event.startDate.getTime());
        }
    };
}

EventFormController.$inject = ["$scope"];

module.exports = EventFormController;