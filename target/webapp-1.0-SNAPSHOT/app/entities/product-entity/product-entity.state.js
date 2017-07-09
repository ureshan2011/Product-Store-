(function() {
    'use strict';

    angular
        .module('webapp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('product-entity', {
            parent: 'entity',
            url: '/product-entity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'webapp.productEntity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-entity/product-entities.html',
                    controller: 'ProductEntityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productEntity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('product-entity-detail', {
            parent: 'entity',
            url: '/product-entity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'webapp.productEntity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-entity/product-entity-detail.html',
                    controller: 'ProductEntityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productEntity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProductEntity', function($stateParams, ProductEntity) {
                    return ProductEntity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'product-entity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('product-entity-detail.edit', {
            parent: 'product-entity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-entity/product-entity-dialog.html',
                    controller: 'ProductEntityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductEntity', function(ProductEntity) {
                            return ProductEntity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-entity.new', {
            parent: 'product-entity',
            url: '/create/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-entity/product-entity-dialog.html',
                    controller: 'ProductEntityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                productName: null,
                                productPrice: null,
                                ProductQty: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('product-entity', null, { reload: true });
                }, function() {
                    $state.go('product-entity');
                });
            }]
        })
        .state('product-entity.edit', {
            parent: 'product-entity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-entity/product-entity-dialog.html',
                    controller: 'ProductEntityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductEntity', function(ProductEntity) {
                            return ProductEntity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-entity.delete', {
            parent: 'product-entity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-entity/product-entity-delete-dialog.html',
                    controller: 'ProductEntityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProductEntity', function(ProductEntity) {
                            return ProductEntity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
