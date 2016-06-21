package com.rockpaperscissors.controller;

import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.helper.HandFactory;
import com.rockpaperscissors.model.OutputTemplate;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.model.RoundsStats;
import com.rockpaperscissors.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;

/**
 * GameController is a Controller receiving requests to play the game.
 * Request and response bodies are sent as JSON objects.
 */

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameConfiguration gameConfiguration;

    /**
     * Receive incoming requests on "/play/{gameModeId}?hand={playersHand} and check parameter validity.
     * If all parameters are valid, calls GameService method to play a round of the game.
     *
     * @param roundsStats: An instance of class RoundStats, which holds info about the game mode and player's choice of hand
     * @return Round: Returns a Round object specifying players' moves, winner and an optional error message.
     */
    @ResponseBody @RequestMapping(value="/play", produces = "application/json")
    public Round playGameRound(@RequestBody RoundsStats roundsStats) {
        Round round;
        int gameModeId = roundsStats.getGameMode();
        String playersHand = roundsStats.getPlayersHand();
        if(GameConfiguration.isValidGameModeId(gameModeId)) {
            gameConfiguration.setGameMode(gameModeId);
            gameService.setStrategyByGameMode(gameModeId);

            if(GameConfiguration.isValidHand(playersHand)) {
                // Can potentially be empty
                String computersHand = HandFactory.getRandomValidHand();
                if(GameConfiguration.isValidHand(computersHand)) {
                    // Return an object of type Round to be displayed as JSON
                    round = gameService.playRound(playersHand, computersHand);
                } else {
                    round = new Round(-1, Collections.emptyMap());
                    round.setMessage(OutputTemplate.ERROR_INVALID_HAND_COMPUTER);
                }
            } else {
                round = new Round(-1, Collections.emptyMap());
                round.setMessage(OutputTemplate.ERROR_INVALID_HAND_PLAYER);
            }
        } else {
            round = new Round(-1, Collections.emptyMap());
            round.setMessage(OutputTemplate.ERROR_INVALID_GAME_MODE);
        }
        return round;
    }
}
