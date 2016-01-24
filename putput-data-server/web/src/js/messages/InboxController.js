var InboxController = function (scope, messages, hotkeys, users) {
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
    
    scope.sendMessage = function () {
        if (!scope.newMessage.recipient) {
            return;
        }
        
        scope.newMessage.message.to = scope.newMessage.recipient.userName;
        messages.sendMessage(scope.newMessage.message).success(function () {
            scope.getMessages();
            scope.compose = false;
        });
    };
    
    scope.openCompose = function () {
      scope.compose = true;  
    };

    scope.closeCompose = function () {
        scope.compose = false;
    };

    hotkeys.add({
        combo: 'ctrl+return',
        allowIn: ['TEXTAREA'],
        callback: scope.sendMessage
    });

    hotkeys.add({
        combo: 'shift+return',
        allowIn: ['TEXTAREA'],
        callback: scope.sendMessage
    });
};

InboxController.$inject = ["$scope", "messages", "hotkeys", "users"];

module.exports = InboxController;