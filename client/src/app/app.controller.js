(function() {

    'use strict';

    angular
        .module('app')
        .controller('AppCtrl', AppCtrl);

    /*@ngInject*/
    function AppCtrl($scope, $rootScope, $state, graphFactory, $http, $log, webSocket) {

        webSocket.forward($rootScope);

        $('aside.sidebar').each(function() {
            var $sidebar = $(this);

            if(/iP(hone|od|ad)/i.test(window.navigator.userAgent) || /Android/i.test(window.navigator.userAgent))
                $sidebar.addClass('is-mobile');

            var refreshFn = function(){$sidebar.nanoScroller();};

            $(document).ready(function(){setTimeout(refreshFn, 0);}); //workaround: scrollbars didn't show up if needed right away
            $sidebar.on('menuPropertiesChanged', function(){setTimeout(refreshFn, 40);}); //workaround: expanding/collapsing items didn't refresh the scrollbars
        });

        var graph = new joint.dia.Graph();

        var graphLocalstorage = graphFactory.graphStack.getCurrent();
        graph.on('paper:ready', function() {
            if (graphLocalstorage) {
                graph.fromJSON(graphLocalstorage);
            } else {
                $http({
                    method: 'GET',
                    url: '/examples/wordcount.json'
                }).then(function successCallback(response) {
                    graph.fromJSON(response.data);
                    graphFactory.saveToLocalStorage(graph);
                }, function errorCallback(response) {
                    $log.error('Error loading /examples/wordcount.json');
                });
            }
        });

        graph.on('add', function(cell) {
            if (cell.attributes.data && cell.attributes.data.modalController) {
                $state.go('app.component', {
                    id: cell.id,
                    modalController: cell.attributes.data.modalController,
                    modalTemplateUrl: cell.attributes.data.modalTemplateUrl
                });
            }
            graphFactory.saveToLocalStorage(graph);
        });

        graph.on('change', function(cell) {
            graphFactory.saveToLocalStorage(graph);
        });

        graph.on('remove', function(cell) {
            graphFactory.saveToLocalStorage(graph);
        });

        function onDropComplete(data, evt) {
            // TODO: replace static sizes
            var posX = evt.x - 250,
                posY = evt.y - 51;

            if (data.type === 'stringFilter') {
                $scope.graph.addCells([graphFactory.renderStringFilter(posX, posY, $state)]);
            } else if (data.type === 'numberFilter') {
                $scope.graph.addCells([graphFactory.renderNumberFilter(posX, posY, $state)]);
            } else if (data.type === 'csvDatasource') {
                $scope.graph.addCells([graphFactory.renderCsvDatasource(posX, posY, $state)]);
            } else if (data.type === 'textDatasource') {
                $scope.graph.addCells([graphFactory.renderTextDatasource(posX, posY, $state)]);
            } else if (data.type === 'map') {
                $scope.graph.addCells([graphFactory.renderMap(posX, posY, $state)]);
            } else if (data.type === 'flatmap') {
                $scope.graph.addCells([graphFactory.renderFlatMap(posX, posY, $state)]);
            } else if (data.type === 'join') {
                $scope.graph.addCells([graphFactory.renderJoin(posX, posY, $state)]);
            } else if (data.type === 'sum') {
                $scope.graph.addCells([graphFactory.renderSum(posX, posY, $state)]);
            } else if (data.type === 'group') {
                $scope.graph.addCells([graphFactory.renderGroup(posX, posY, $state)]);
            } else if (data.type === 'reduce') {
                $scope.graph.addCells([graphFactory.renderReduce(posX, posY, $state)]);
            } else if (data.type === 'csvDatasink') {
                $scope.graph.addCells([graphFactory.renderCsvDatasink(posX, posY, $state)]);
            } else if (data.type === 'customFilter') {
                $scope.graph.addCells([graphFactory.renderCustomFilter(posX, posY, $state)]);
            }
        }

        $scope.onDropComplete = onDropComplete;
        $rootScope.graph = graph;

    }

})();
