function MessagesApi(http, api) {
    this.getMessages = function () {
        return api.withLink("messages", function (messagesLink) {
            return http.get(messagesLink);
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