function MessagesApi(http, api) {
    this.getMessages = function (page) {
        return api.withLink("messages", function (messagesLink) {
            return http.get(messagesLink + (page ? '?page=' + page : ''));
        });
    };

    this.sendMessage = function(message) {
        return api.withLink('message', function (messageLink) {
            return http.post(messageLink, message); 
        });  
    };
}

MessagesApi.$inject = ['$http', 'api'];

module.exports = MessagesApi;