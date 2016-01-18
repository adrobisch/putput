function ShareDialogController(scope, modalInstance, item) {
    scope.item = item;

    scope.ok = function () {
        modalInstance.close();
    };

    scope.cancel = function () {
        modalInstance.dismiss('cancel');
    };
}

ShareDialogController.$inject = ['$scope', '$uibModalInstance', 'item'];

module.exports = ShareDialogController;