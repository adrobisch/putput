var InboxController = function (scope, messages) {
    scope.getMessages = function () {
        messages.getMessages().success(function (messages) {
            scope.messages = messages;
        });
    };

    scope.getMessages();
};

InboxController.$inject = ["$scope", "messages"];

module.exports = InboxController;