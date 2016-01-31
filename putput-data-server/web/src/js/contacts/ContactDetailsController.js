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
    scope.contact.emails.push({
      "address" : "mail@example.org",
      "type" : "HOME"
    });
  };

  scope.addNewPhoneNumber = function () {
    scope.contact.phoneNumbers.push({
      "number" : "+49123456789",
      "type" : "MOBILE"
    });
  };

  scope.addNewAddress = function () {
    scope.contact.contactAddresses.push({
      "city" : "City",
      "postalCode" : "Postal Code",
      "street" : "Street",
      "houseNo": "42",
      "country": "Germany",
      "type" : "HOME"
    });
  };

  scope.addNewInternetIdentifier = function () {
    scope.contact.internetIdentifiers.push({
      "type" : "TWITTER",
      "idValue" : ""
    });
  };

  scope.remove = function (array, index) {
    array.splice(index, 1);
  };

  scope.init();
};

ContactDetailsController.$inject = ['$scope', '$routeParams', 'contacts', '$alert'];

module.exports = ContactDetailsController;