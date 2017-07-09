(function() {
    'use strict';

    angular
        .module('webapp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('resources/api/account/change_password', {}, {});

        return service;
    }
})();
