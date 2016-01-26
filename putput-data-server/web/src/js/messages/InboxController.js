var emojify = require("emojify.js");

var InboxController = function (scope, messages, hotkeys, users, interval) {
    scope.newMessage = {
        "recipient": null,
        "message": {
            "type": "chat"    
        }
    };
    
    scope.users = [];
    
    scope.init = function () {
      scope.getUsers();
      scope.getMessages();  
    };
    
    scope.getUsers = function (search) {
        users.getUsers(function (userPage) {
            scope.users = userPage.users;
        }, search);
    };
    
    scope.getMessages = function () {
        messages.getMessages().success(function (messages) {
            scope.messages = messages.data;
        });
    };
    
    scope.formatText = function (text) {
        return emojify.replace(text);
    };

    scope.loadNext = function () {
        messages.nextPage(scope.messages).success(function (messages) {
            scope.messages = messages;
        });
    };

    scope.loadPrevious = function () {
        messages.previousPage(scope.messages).success(function (messages) {
            scope.messages = messages;
        });
    };

    scope.start = function (messages) {
        if (!messages) {
            return 0;
        }
        return messages.currentPage * messages.pageSize;
    };

    scope.end = function (messages) {
        if (!messages) {
            return 0;
        }
        return (messages.currentPage + 1) * messages.pageSize;
    };

    scope.totalCount = function () {
        if (!scope.messages) {
            return 0;
        }
        return scope.messages.count;
    };

    scope.sendMessage = function () {
        if (!scope.newMessage.recipient) {
            return;
        }
        
        scope.newMessage.message.to = scope.newMessage.recipient.userName;
        messages.sendMessage(scope.newMessage.message).success(function () {
            scope.getMessages();
            scope.newMessage.message.text = null;
        });
    };
    
    hotkeys.bindTo(scope).add({
        description: "send message",
        combo: 'ctrl+return',
        allowIn: ['TEXTAREA'],
        callback: scope.sendMessage
    });

    hotkeys.bindTo(scope).add({
        description: "send message",
        combo: 'shift+return',
        allowIn: ['TEXTAREA'],
        callback: scope.sendMessage
    });

    scope.reloadPromise = interval(scope.getMessages, 3000);

    scope.$on('$destroy', function() {
        interval.cancel(scope.reloadPromise);
    });
};

InboxController.$inject = ["$scope", "messages", "hotkeys", "users", "$interval"];

module.exports = InboxController;