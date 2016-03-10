var emojiCodes = require('../common/Emojis');

var ItemInputController = function (scope, timeline, rootScope, hotkeys, users) {
    scope.rows = scope.rows || 3;
    
    scope.searchUsers = function (term) {
        users.getUsers(function (userPage) {
            scope.users = userPage.users;
        }, term);
    };

    scope.findEmoji = function (term) {
        if (!term) {
            return;
        }

        scope.emojis = _.map(_.filter(emojiCodes, function (code) {
            return _.startsWith(code, ":" + term);
        }), function (foundCode) {
            return foundCode.replace(/:/g, '');
        });
    };
};

ItemInputController.$inject = ["$scope", "timeline", "$rootScope", "hotkeys", "users"];

module.exports = function() {
    return {
        template: require("./item-input.html"),
        controller: ItemInputController,
        scope: {
            rows: "@",
            placeholder: "@",
            label: "@",
            onSubmit: "&",
            input: "=bind"
        }
    };
};