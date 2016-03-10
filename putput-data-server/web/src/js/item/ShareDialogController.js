function ShareDialogController(scope, timeline, rootScope, modalInstance, item) {
    scope.item = item;

    scope.submit = function () {
        timeline.postItem(scope.comment || "", scope.item.streamItem.id).success(function() {
            rootScope.$emit("item.created");
            modalInstance.close();
        });
    };

    scope.cancel = function () {
        modalInstance.dismiss('cancel');
    };
}

ShareDialogController.$inject = ['$scope', 'timeline', '$rootScope', '$uibModalInstance', 'item'];

module.exports = ShareDialogController;