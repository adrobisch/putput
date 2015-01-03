var _ = require("lodash");
var emojiCodes = require('../common/Emojis');

var HomeController = function (scope, timeline, rootScope, hotkeys, users) {
    scope.stream = [];
    scope.imageUrl = null;
    scope.filter = "all";
    scope.newPut = null;

    scope.users = [];
    scope.emojis = [];

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
    
    scope.setFilter = function(filter) {
        scope.filter = filter;
    };

    scope.put = function () {
        timeline.postItem(scope.newPut).success(function() {
            rootScope.$emit("item.created");
            scope.newPut = "";
        })
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
HomeController.$inject = ["$scope", "timeline", "$rootScope", "hotkeys", "users"];

module.exports = HomeController;