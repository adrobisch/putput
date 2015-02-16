var ContactsController =  function(scope, contacts) {
  scope.showCreateForm = false;
  scope.newContact = {};
  scope.uploadConfig = {
    target:'/api/contacts/import', testChunks: false
  };
  scope.inputAttributes = {accept:'text/vcard,text/x-vcard,text/vCard,.vcf'};

  scope.toggleForm = function () {
    scope.showCreateForm = !scope.showCreateForm;
  };

  scope.submitNewContact = function() {
    contacts
        .create(scope.newContact)
        .success(scope.loadContacts);
  };

  scope.onUploadCompleted = function (file, message) {
    console.log("file", file, message);
    scope.loadContacts();
  };

  scope.loadContacts = function () {
    contacts.list().success(function withContacts(contactList){
      scope.contacts = contactList.data.contacts;
    });
  };

  scope.init = scope.loadContacts;

  scope.init();
};

ContactsController.$inject = ['$scope', 'contacts'];

module.exports = ContactsController;