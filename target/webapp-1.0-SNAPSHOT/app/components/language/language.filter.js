(function() {
    'use strict';

    angular
        .module('webapp')
        .filter('findLanguageFromKey', findLanguageFromKey);

    function findLanguageFromKey() {
        return findLanguageFromKeyFilter;

        function findLanguageFromKeyFilter(lang) {
            return {
                'ca': 'Català',
                'cs': 'Český',
                'da': 'Dansk',
                'de': 'Deutsch',
                'el': 'Ελληνικά',
                'en': 'English',
                'es': 'Español',
                'fr': 'Français',
                'gl': 'Galego',
                'hu': 'Magyar',
                'hi': 'हिंदी',
                'it': 'Italiano',
                'ja': '日本語',
                'ko': '한국어',
                'mr': 'मराठी',
                'nl': 'Nederlands',
                'pl': 'Polski',
                'pt-br': 'Português (Brasil)',
                'pt-pt': 'Português',
                'ro': 'Română',
                'ru': 'Ру�?�?кий',
                'sk': 'Slovenský',
                'sv': 'Svenska',
                'ta': 'தமிழ�?',
                'tr': 'Türkçe',
                'zh-cn': '中文（简体）',
                'zh-tw': '�?體中文'
            }[lang];
        }
    }
})();
