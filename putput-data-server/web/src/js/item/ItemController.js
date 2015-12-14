var itemController = function (scope, rootScope, timeline, route) {
    scope.itemId = route.current.params["itemId"];
    scope.theItem = null;

    scope.toggleMarker = function (item, markerInfoProperty, type, markCallback, unmarkCallback) {
        if (!item.markerInfo[markerInfoProperty]) {
            return timeline.markItem(item.streamItem.id, type).success(function () {
                item.markerInfo[markerInfoProperty]= true;
                if (markCallback) {
                    markCallback();
                }
            })
        } else {
            return timeline.unmarkItem(item.streamItem.id, type).success(function () {
                item.markerInfo[markerInfoProperty] = false;
                if (unmarkCallback) {
                    unmarkCallback();
                }
            })
        }
    };

    scope.toggleFavorite = function(item) {
        this.toggleMarker(item, "isFavorite", "favorite");
    };

    scope.toggleLater = function(item) {
        this.toggleMarker(item, "isReadLater", "later");
    };

    scope.toggleLike = function(item) {
        this.toggleMarker(item, "isLike", "like", function() {
            item.markerInfo.likes++;
        }, function () {
            item.markerInfo.likes--;
        });
    };

    scope.toggleDislike = function(item) {
        this.toggleMarker(item, "isDislike", "dislike", function() {
            item.markerInfo.dislikes++;
        }, function () {
            item.markerInfo.dislikes--;
        });
    };

    scope.getItem = function (itemId) {
        timeline.getItem(itemId).success(function (data) {
            scope.theItem = data;
        });
    };

    scope.canDelete = function (item) {
        return item._links.delete != undefined;
    };

    scope.deleteStreamItem = function(item) {
        timeline.deleteItem(item).success(function onSuccess() {
            rootScope.$emit("item.deleted");
        });
    };

    if(scope.itemId) {
        scope.getItem(scope.itemId);
    }
};

itemController.$inject = ["$scope", "$rootScope", "timeline", "$route"];
module.exports = itemController;