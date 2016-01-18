var emojiCodes = require('../common/Emojis');

var ItemInputController = function (scope, timeline, rootScope, hotkeys, users) {
    scope.put = function () {
        if (scope.itemRef) {
            console.log("itemref", scope.itemRef);
        }

        timeline.postItem(scope.newPut, scope.itemRef).success(function() {
            rootScope.$emit("item.created");
            if (scope.onSubmit) {
                scope.onSubmit();
            }
            scope.newPut = "";
        })
    };

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

    hotkeys.add({
        combo: 'ctrl+return',
        allowIn: ['TEXTAREA'],
        callback: scope.put
    });

    hotkeys.add({
        combo: 'shift+return',
        allowIn: ['TEXTAREA'],
        callback: scope.put
    });
};

ItemInputController.$inject = ["$scope", "timeline", "$rootScope", "hotkeys", "users"];

module.exports = function() {
    return {
        template: require("./item-input.html"),
        controller: ItemInputController,
        scope: {
            onSubmit: "&",
            itemRef: "="
        }
    };
};