(function() {
    'use strict';

    angular
        .module('webapp')
        .factory('PasswordResetFinish', PasswordResetFinish);

    PasswordResetFinish.$inject = ['$resource'];

    function PasswordResetFinish($resource) {
        var service = $resource('resources/api/account/reset_password/finish', {}, {});

        return service;
    }
})();
