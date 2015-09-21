var webpack = require("webpack");

var webPackConfig = function (entry, targetDir, filename) {
    return {
        module: {
            loaders: [ 
                { test: /\.json$/, loader: "json" },
                { test: /\.html/, loader: "html" }
            ]
        },

        plugins: [],

        entry: entry,

        output: {
            path: targetDir,
            filename: filename
        },

        stats: {
            colors: false,
            modules: true,
            reasons: true
        }
    };
};

module.exports = webPackConfig;