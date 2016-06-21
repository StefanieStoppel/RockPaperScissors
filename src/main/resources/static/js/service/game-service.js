'use strict';

App.factory('GameService', ['$http', '$q', function($http, $q){

    return {

        playRound: function (playersHand, gameModeId) {
            return $http({
                url: '/play',
                method: "POST",
                data: {gameMode: gameModeId, playersHand: playersHand},
                headers: {'Content-Type': 'application/json'}
            }).success(function (data) {
                return data;
            }).error(function (data, status) {
                console.error('Error while playing a round. Status: ' + status);
                return $q.reject(data);
            });
        }
    }
}]);