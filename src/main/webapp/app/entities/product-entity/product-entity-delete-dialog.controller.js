(function() {
    'use strict';

    angular
        .module('webapp')
        .controller('ProductEntityDeleteController',ProductEntityDeleteController);

    ProductEntityDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProductEntity'];

    function ProductEntityDeleteController($uibModalInstance, entity, ProductEntity) {
        var vm = this;

        vm.productEntity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProductEntity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
