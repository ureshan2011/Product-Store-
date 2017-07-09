(function() {
    'use strict';

    angular
        .module('webapp')
        .controller('ProductEntityDetailController', ProductEntityDetailController);

    ProductEntityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductEntity'];

    function ProductEntityDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductEntity) {
        var vm = this;

        vm.productEntity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('webapp:productEntityUpdate', function(event, result) {
            vm.productEntity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
