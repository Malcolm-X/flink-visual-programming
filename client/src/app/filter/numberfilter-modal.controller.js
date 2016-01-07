(function() {

    'use strict';

    angular
        .module('app.filter')
        .controller('NumberfilterModalCtrl', FilterModalCtrl);

    /*@ngInject*/
    function FilterModalCtrl($scope, $rootScope, $uibModalInstance, $stateParams, $timeout, graphFactory, $log) {

        var cell = $rootScope.graph.getCell($stateParams.id);

        $scope.operationTypes = [{
            label: '=',
            key: '='
        }, {
            label: '>',
            key: '>'
        }, {
            label: '≥',
            key: '>='
        }, {
            label: '<',
            key: '<'
        }, {
            label: '≤',
            key: '<='
        }];

        $scope.numberfilter = {
            inputIndex: cell.attributes.data.inputIndex,
            operationType: cell.attributes.data.operationType,
            compareValue: cell.attributes.data.compareValue
        };

        $scope.save = save;
        $scope.cancel = cancel;

        function save() {
            cell.attributes.data.inputIndex = $scope.numberfilter.inputIndex;
            cell.attributes.data.operationType = $scope.numberfilter.operationType;
            cell.attributes.data.compareValue = $scope.numberfilter.compareValue;
            graphFactory.saveToLocalStorage($rootScope.graph);
            $uibModalInstance.close();
        }

        function cancel() {
            $uibModalInstance.close();
        }

    }

})();