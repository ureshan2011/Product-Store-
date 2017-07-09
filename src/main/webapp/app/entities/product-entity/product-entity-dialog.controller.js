(function() {
    'use strict';

    angular
        .module('webapp')
        .controller('ProductEntityDialogController', ProductEntityDialogController);

    ProductEntityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductEntity'];

    function ProductEntityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductEntity) {
        var vm = this;

        vm.productEntity = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.productEntity.id !== null) {
                ProductEntity.update(vm.productEntity, onSaveSuccess, onSaveError);
            } else {
                ProductEntity.save(vm.productEntity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('webapp:productEntityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
