var ApiService = function Api(http, q) {
  this._serviceDocument = undefined;

  this.toHttpLikePromise = function (promise) {
    return {
      success: function (successCallback) {
        promise.then(successCallback, null);
        return this;
      },
      error: function(errorCallback) {
        promise.then(null, errorCallback);
        return this;
      },
      then: function (successCallback, errorCallback) {
        promise.then(successCallback, errorCallback);
        return this;
      }
    }
  };

  this.withLink = function(linkName, linkToPromiseFn) {
    var deferred = q.defer();
    var self = this;

    if (!this._serviceDocument) {
      this.getApi().success(function (serviceDocument) {
        self._serviceDocument = serviceDocument;
        if (!serviceDocument["_links"][linkName]) {
          throw new Error("link does not exist: " + linkName);
        }
        deferred.resolve(serviceDocument["_links"][linkName].href);
        self.serviceDocumentPromise = null;
      });
    } else {
      deferred.resolve(this._serviceDocument["_links"][linkName].href);
    }

    return this.toHttpLikePromise(deferred.promise.then(linkToPromiseFn));
  };

  this.getApi = function () {
    if (!this.getApiPromise) {
      return this.getApiPromise = http.get('api');
    }
    return this.getApiPromise;
  };

};

ApiService.$inject = [ '$http', '$q'];

module.exports = ApiService;