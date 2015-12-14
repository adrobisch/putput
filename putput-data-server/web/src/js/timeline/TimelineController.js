var timelineController = function TimelineController(scope, timeline, rootScope) {
    scope.pageLocked = false;
    scope.stream = null;

    rootScope.$on("item.deleted", function () {
        scope.getStream();
    });

    rootScope.$on("item.created", function () {
        scope.getStream();
    });

    scope.$watch("filter", function (newValue, oldValue) {
        scope.getStream();
    });

    scope.getStream = function () {
        var onSuccess = function onSuccess(stream) {
            scope.stream = stream;
        };

        scope.loadStream().success(onSuccess);
    };

    scope.loadStream = function () {
        if (scope.profile) {
            return timeline.getProfileStream(scope.profile);
        }

        if (scope.filter) {
            switch(scope.filter) {
                case "favorites":
                    return timeline.getFavoriteStream();
                    break;
                case "later":
                    return timeline.getLaterStream();
                    break;
                default:
                    console.log("unknown filter: " + scope.filter + ", defaulting to full stream");
                    return timeline.getStream();
            }
        }

        return timeline.getStream();
    };

    scope.nextPage = function () {
        if (scope.stream == null) {
            scope.getStream();
            return;
        }

        if (scope.pageLocked == false) {
            scope.pageLocked = true;
            var nextPageLink = scope.stream["_links"]["nextPage"];

            if (nextPageLink) {
                timeline.getStreamItems(nextPageLink.href).success(function onSuccess(stream) {
                    scope.pageLocked = false;
                    scope.stream.items = scope.stream.items.concat(stream.items);
                    scope.stream.page = stream.page;
                    scope.stream["_links"] = stream["_links"];
                }).error(function onError() {
                    scope.pageLocked = false;
                });
            }
        }
    };
};
timelineController.$inject = ["$scope", "timeline", "$rootScope"];

module.exports = timelineController;