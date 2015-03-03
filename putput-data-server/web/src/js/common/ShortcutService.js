var ShortcutService = function (hotkeys) {
  document.addEventListener("keydown", function(e) {
    if (e.keyCode == 83 && (navigator.platform.match("Mac") ? e.metaKey : e.ctrlKey)) {
      e.preventDefault();
    }
  }, false);

  return {
    add: function(combo, callback, scope) {
      var currentHotkeys = scope ?  hotkeys.bindTo(scope) : hotkeys;

      currentHotkeys.add({
        combo: combo,
        allowIn: ['TEXTAREA'],
        callback: callback
      });
    }
  };
};

ShortcutService.$inject = ['hotkeys'];

module.exports = ShortcutService;
