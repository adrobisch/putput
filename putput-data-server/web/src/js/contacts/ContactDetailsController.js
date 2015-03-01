var ContactDetailsController =  function(scope, routeParams, contacts, alert) {
  scope.contactId = routeParams.id;

  scope.init = function () {
    scope.loadContact();
  };

  scope.loadContact = function () {
    contacts.get(scope.contactId).success(function (contactResponse) {
      scope.contact = contactResponse.data;
    });
  };

  scope.updateContact = function () {
    contacts.update(scope.contact).success(function () {
      alert.info("Success", "Contact Updated!");
      scope.loadContact();
    }).error(function () {
      alert.error("Error", "Unable to update!");
    });
  };

  scope.addNewMail = function () {
    if(scope.newMail) {
      scope.contact.emails.push({
        "address" : scope.newMail,
        "type" : "HOME"
      });
      scope.newMail = null;
    }
  };

  scope.addNewPhoneNumber = function () {
    if(scope.newPhoneNumber) {
      scope.contact.phoneNumbers.push({
        "number" : scope.newPhoneNumber,
        "type" : "MOBILE"
      });
      scope.newPhoneNumber = null;
    }
  };

  scope.addNewAddress = function () {
    scope.contact.contactAddresses.push({
      "city" : "City",
      "postalCode" : "Postal Code",
      "street" : "Street",
      "type" : "HOME"
    });
  };

  scope.remove = function (array, index) {
    array.splice(index, 1);
  };

  scope.init();
};

ContactDetailsController.$inject = ['$scope', '$routeParams', 'contacts', '$alert'];

module.exports = ContactDetailsController;