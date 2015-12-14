function SearchService(rootScope) {
    var searchEventKey = "search.event";

    this.enabled = false;

    this.query = function (q) {
        rootScope.$emit(searchEventKey, {"query": q});
    };

    this.onSearch = function (searchHandler, scope) {
        var listener = function (event, payload) {
            searchHandler(payload.query);
        };

        var registration = rootScope.$on(searchEventKey, listener);

        if (scope) {
            scope.$on('$destroy', function() {
                registration();
            });
        }

        return registration;
    };

    this.setEnabled = function (enabled) {
      this.enabled = enabled;
    };

    this.isEnabled = function () {
        return this.enabled;
    }
}

SearchService.$inject = ["$rootScope"];

module.exports = SearchService;