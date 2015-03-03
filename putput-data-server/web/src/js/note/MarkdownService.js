var marked = require("marked");

var MarkdownService = function (sce) {
  return {
    render: function(markdownContent) {
      return sce.trustAsHtml(marked(markdownContent));
    }
  };
};

MarkdownService.$inject = ['$sce'];

module.exports = MarkdownService;
