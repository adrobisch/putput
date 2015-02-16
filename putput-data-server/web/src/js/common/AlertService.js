var AlertService = function ($timeout, $rootScope) {
  var alertService = {};
  $rootScope.alerts = [];

  alertService.error = function (title, msg, timeout) {
    alertService.add("danger", title, msg, timeout);
  };

  alertService.warning = function (title, msg, timeout) {
    alertService.add("warning", title, msg, timeout);
  };

  alertService.info = function (title, msg, timeout) {
    alertService.add("success", title, msg, timeout);
  };

  alertService.add = function (type, title, msg, timeout) {
    $rootScope.alerts.push({
      type: type,
      title: title,
      msg: msg,
      close: function () {
        return alertService.closeAlert(this);
      }
    });

    if (typeof timeout == 'undefined') {
      timeout = 8000;
    }

    if (timeout) {
      $timeout(function () {
        alertService.closeAlert(this);
      }, timeout);
    }
  };

  alertService.closeAlert = function (alert) {
    return alertService.closeAlertIdx($rootScope.alerts.indexOf(alert));
  };

  alertService.closeAlertIdx = function (index) {
    return $rootScope.alerts.splice(index, 1);
  };

  alertService.clear = function () {
    return $rootScope.alerts = [];
  };

  return alertService;
};

AlertService.$inject = ["$timeout", "$rootScope"];

module.exports = AlertService;
