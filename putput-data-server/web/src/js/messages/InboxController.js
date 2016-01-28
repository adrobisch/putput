var emojify = require("emojify.js");

var InboxController = function (scope, messages, hotkeys, users, routeParams, location) {
    scope.newMessage = {
        "recipient": null,
        "message": {
            "type": "chat"    
        }
    };
    
    scope.users = [];
    scope.page = routeParams.page;
    
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
        messages.getMessages(scope.page || (scope.messages ? scope.messages.currentPage : null)).success(function (messages) {
            scope.messages = messages.data;
        });
    };

    scope.deleteMessage = function (message) {
        messages.deleteMessage(message).success(scope.getMessages);
    };
    
    scope.formatText = function (text) {
        return emojify.replace(text);
    };

    scope.loadNext = function () {
        location.path("/inbox/" + (scope.messages.currentPage + 1));
    };

    scope.loadPrevious = function () {
        location.path("/inbox/" + (scope.messages.currentPage - 1));
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
    }).add({
        description: "send message",
        combo: 'shift+return',
        allowIn: ['TEXTAREA'],
        callback: scope.sendMessage
    }).add({
        description: "reload messages",
        combo: 'r',
        allowIn: ['TEXTAREA'],
        callback: scope.getMessages
    });

};

InboxController.$inject = ["$scope", "messages", "hotkeys", "users", "$routeParams", "$location"];

module.exports = InboxController;