'use strict';

App.controller('GameController', ['$scope', 'GameService', function($scope, GameService) {
    $scope.gameMode = {
        id: 0
    };
    var self = this;
    self.round={count:null,moves:{},winner:null,message:''};

    self.playRound = function(playersHand){
        GameService.playRound(playersHand, $scope.gameMode.id)
            .then(
                function(d) {
                    self.round = d.data;
                    //console.log("Round result:", self.round);
                    //console.log("Moves:", self.round.moves);
                },
                function(errResponse){
                    console.error('Error while playing a round. ' + errResponse);
                }
            );
    };

    self.play = function(playersHand) {
        //console.log('Playing a round with hand ' + playersHand + ' and game mode ' + $scope.gameMode.id);
        self.playRound(playersHand);

        self.reset();
    };


    self.reset = function(){
        self.user={count:null,moves:{},winner:null,message:''};
    };

}]);