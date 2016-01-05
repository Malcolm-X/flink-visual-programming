(function() {

    'use strict';

    angular
        .module('app.datasource')
        .controller('DatasourceModalCtrl', DatasourceModalCtrl);

    /*@ngInject*/
    function DatasourceModalCtrl($scope, $rootScope, $uibModalInstance, $stateParams, $timeout, $log) {

        var cell = $rootScope.graph.getCell($stateParams.id);

        $scope.dataTypes = [{
            label: '- none -',
            key: 'none',
        }, {
            label: 'String',
            key: 'string'
        }, {
            label: 'Float',
            key: 'float'
        }, {
            label: 'Integer',
            key: 'integer'
        }];

        $scope.datasource = {
            path: cell.attributes.data.path,
            countColumns: cell.attributes.data.countColumns,
            columns: cell.attributes.data.columns
        };

        $scope.save = save;
        $scope.cancel = cancel;

        $scope.$watch('datasource.countColumns', function(newValue, oldValue) {
            var i;
            if ($scope.datasource.columns.length < newValue) {
                for (i = $scope.datasource.columns.length; i < newValue; i++) {
                    $scope.datasource.columns.push({
                        column: i,
                        type: angular.extend({}, $scope.dataTypes[0])
                    });
                }
            } else {
                for (i = $scope.datasource.columns.length; i > newValue; i--) {
                    $scope.datasource.columns.pop();
                }
            }
        });

        function save() {
            cell.attributes.data.path = $scope.datasource.path;
            cell.attributes.data.countColumns = $scope.datasource.countColumns;
            cell.attributes.data.columns = $scope.datasource.columns;
            $uibModalInstance.close();
        }

        function cancel() {
            $uibModalInstance.close();
        }

    }

})();
