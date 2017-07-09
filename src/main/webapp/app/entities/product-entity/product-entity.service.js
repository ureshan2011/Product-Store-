(function() {
    'use strict';
    angular
        .module('webapp')
        .factory('ProductEntity', ProductEntity);

    ProductEntity.$inject = ['$resource'];

    function ProductEntity ($resource) {
        var resourceUrl =  'resources/api/product-entity/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
