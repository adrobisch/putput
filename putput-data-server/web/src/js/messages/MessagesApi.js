function MessagesApi(http, api) {
    this.getMessages = function () {
        return api.withLink("messages", function (messagesLink) {
            return http.get(messagesLink);
        });
    };

    this.nextPage = function (messagesList) {
        if (!messagesList._links.nextPage) {
            return;
        }
        return http.get(messagesList._links.nextPage.href);
    };

    this.previousPage = function (messagesList) {
        if (!messagesList._links.previousPage) {
            return;
        }
        return http.get(messagesList._links.previousPage.href);
    };
    
    this.sendMessage = function(message) {
        return api.withLink('message', function (messageLink) {
            return http.post(messageLink, message); 
        });  
    };
}

MessagesApi.$inject = ['$http', 'api'];

module.exports = MessagesApi;