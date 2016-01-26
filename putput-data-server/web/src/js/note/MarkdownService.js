var MarkdownService = function (sce, filter) {
  return {
    render: function(markdownContent) {
      return sce.trustAsHtml(filter('markdown')(markdownContent));
    }
  };
};

MarkdownService.$inject = ['$sce', '$filter'];

module.exports = MarkdownService;
