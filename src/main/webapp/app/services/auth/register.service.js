(function () {
    'use strict';

    angular
        .module('webapp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('resources/api/register', {}, {});
    }
})();
