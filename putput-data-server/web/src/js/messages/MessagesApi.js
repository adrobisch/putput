function MessagesApi(http, api) {
    this.getMessages = function (page, withUser) {
        return api.withLink("messages", function (messagesLink) {
            return http.get(messagesLink + "?" + (page ? 'page=' + page : '') + (withUser ? '&with=' + withUser : ''));
        });
    };

    this.sendMessage = function(message) {
        return api.withLink('message', function (messageLink) {
            return http.post(messageLink, message); 
        });  
    };

    this.deleteMessage = function (message) {
        return http.delete(message._links["delete"].href);
    };
}

MessagesApi.$inject = ['$http', 'api'];

module.exports = MessagesApi;