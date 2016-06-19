'use strict';

App.factory('GameService', ['$http', '$q', function($http, $q){

    return {

        playRound: function(playersHand, gameModeId){
            return $http.get('http://localhost:8080/play/'+gameModeId+'?hand='+playersHand)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while playing a round.');
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);