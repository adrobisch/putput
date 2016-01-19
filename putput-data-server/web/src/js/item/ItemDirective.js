module.exports = function () {
    return {
        controller: require("./ItemController"),
        transclude: true,
        scope: {
            "item" : "=",
            "itemId": "=",
            "plain": "="
        },
        template: require('./item.html')
    };
};