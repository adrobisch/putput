var SearchBarController =  function(scope, search) {
    scope.query = "";

    scope.search = function () {
        search.query(scope.query);
    };
};

SearchBarController.$inject = ['$scope', 'search'];

module.exports = SearchBarController;