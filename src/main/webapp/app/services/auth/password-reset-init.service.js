(function() {
    'use strict';

    angular
        .module('webapp')
        .factory('PasswordResetInit', PasswordResetInit);

    PasswordResetInit.$inject = ['$resource'];

    function PasswordResetInit($resource) {
        var service = $resource('resources/api/account/reset_password/init', {}, {});

        return service;
    }
})();
