function SearchService(rootScope) {
    var searchEventKey = "search.event";

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
}

SearchService.$inject = ["$rootScope"];

module.exports = SearchService;