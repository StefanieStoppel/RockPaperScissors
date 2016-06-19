'use strict';

App.controller('GameController', ['$scope', 'GameService', function($scope, GameService) {
    var self = this;
    self.round={count:null,moves:{},winner:null,message:''};

    self.playRound = function(playersHand, gameModeId){
        GameService.playRound(playersHand, gameModeId)
            .then(
                function(d) {
                    self.round = d;
                    console.log("Round result:", self.round);
                    console.log("Moves:", self.round.moves);
                },
                function(errResponse){
                    console.error('Error while playing a round. ' + errResponse);
                }
            );
    };

    self.play = function(playersHand, gameModeId) {
        console.log('Playing a round with hand ' + playersHand
            + ' and game mode ' + gameModeId);
        self.playRound(playersHand, gameModeId);

        self.reset();
    };


    self.reset = function(){
        self.user={count:null,moves:{},winner:null,message:''};
    };

}]);