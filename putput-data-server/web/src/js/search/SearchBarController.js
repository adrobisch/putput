var SearchBarController =  function(scope, search) {
    scope.query = "";

    scope.search = function () {
        search.query(scope.query);
    };

    scope.isSearchEnabled = function () {
        return search.isEnabled();
    };
};

SearchBarController.$inject = ['$scope', 'search'];

module.exports = SearchBarController;