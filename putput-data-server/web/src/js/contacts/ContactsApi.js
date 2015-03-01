function ContactsApi(http, api) {
  this.create = function(newContact) {
    return api.withLink("contact", function (contactLink){
      return http.post(contactLink, newContact);
    });
  };

  this.get = function (id) {
    return api.withLink("contact", function (contactLink) {
      return http.get(contactLink.replace("{id}", id));
    });
  };

  this.list = function() {
    return api.withLink("contacts", function (contactsLink){
      return http.get(contactsLink);
    });
  };

  this.update = function (contact) {
    console.log("updating", contact);
    return http.put(contact["_links"]["self"].href, contact);
  };

  this.delete = function(contact) {
    console.log("deleting", contact);
    return http.delete(contact["_links"]["self"].href);
  }
}

ContactsApi.$inject = ["$http", "api"];

module.exports = ContactsApi;