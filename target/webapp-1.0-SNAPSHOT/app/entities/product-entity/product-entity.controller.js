(function() {
    'use strict';

    angular
        .module('webapp')
        .controller('ProductEntityController', ProductEntityController);

    ProductEntityController.$inject = ['$scope', '$state', 'ProductEntity'];

    function ProductEntityController ($scope, $state, ProductEntity) {
        var vm = this;
        
        vm.productEntities = [];

        loadAll();

        function loadAll() {
            ProductEntity.query(function(result) {
                vm.productEntities = result;
            });
        }
    }
})();
