function MessagesApi(http, api) {
    this.getMessages = function () {
        return api.withLink("messages", function (messagesLink) {
            return http.get(messagesLink);
        });
    };
}

MessagesApi.$inject = ['$http', 'api'];

module.exports = MessagesApi;