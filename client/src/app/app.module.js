(function() {

    'use strict';

    angular.module('app', [
        'ngAnimate',
        'ngTouch',
        'ui.router',
        'ui.bootstrap',
        'angularMoment',
        'ngDraggable',
        'LocalStorageModule',
        'ui.codemirror',
        'toaster',
        'cfp.hotkeys',
        'ngWebSocket',

        'app.menu',
        'app.console',
        'app.datasource',
        'app.datasink',
        'app.filter',
        'app.group',
        'app.sum',
        'app.flatmap',
        'app.map',
        'app.reduce',
        'app.generalsettings',
        'app.loadingmodal'
    ]);

})();
