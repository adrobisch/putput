var emojify = require("emojify.js");

function TimelineApi(http, sce, q) {
    emojify.setConfig({
       ignore_emoticons: false,
       img_dir: '/vendor/emojify.js/dist/images/basic'
    });

    this.transformItemContent = function (item) {
        item.streamItem.content = sce.trustAsHtml(emojify.replace(item.streamItem.content));
        return item;
    };

    this.getStreamItems = function(path) {
        var deferred = q.defer();
        var self = this;
        return http.get(path).success(function(stream) {
            // pre-process items with sce using proxy promise
            var trustedData = stream.items.map(function (item) {
                return self.transformItemContent(item);
            });
            deferred.resolve(trustedData);
        }).error(function(){
            deferred.reject();
        });
    };

    this.getStream = function () {
        return this.getStreamItems("/api/stream");
    };

    this.getFavoriteStream = function () {
        return this.getStreamItems("/api/stream?type=favorite");
    };

    this.getLaterStream = function () {
        return this.getStreamItems("/api/stream?type=later");
    };

    this.getProfileStream = function(profileId) {
        return this.getStreamItems("/api/stream?profile=" + profileId);
    };

    this.getItem = function(itemId) {
        var deferred = q.defer();
        var self = this;
        return http.get("/api/stream/item/" + itemId).success(function(item) {
            deferred.resolve(self.transformItemContent(item));
        }).error(function(){
            deferred.reject();
        });
    };

    this.postItem = function (itemContent, itemRef) {
        return http.post("/api/stream/items", {content: itemContent, reference: itemRef});
    };

    this.deleteItem = function(item) {
        return http.delete(item._links.delete.href);
    };

    this.markItem = function (itemId, type) {
        return http.post("/api/marker", {itemId: itemId, markerType: type});
    };

    this.unmarkItem = function (itemId, type) {
        return http.delete("/api/marker?itemId=" + itemId + "&markerType=" + type);
    };
}

TimelineApi.$inject = ["$http", "$sce", "$q"];

module.exports = TimelineApi;