function ContactsApi(http, api) {
  this.create = function(newContact) {
    return api.withLink("contact", function (contactLink){
      return http.post(contactLink, newContact);
    });
  };

  this.list = function() {
    return api.withLink("contacts", function (contactsLink){
      return http.get(contactsLink);
    });
  };
}

ContactsApi.$inject = ["$http", "api"];

module.exports = ContactsApi;