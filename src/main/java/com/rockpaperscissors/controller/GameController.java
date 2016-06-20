package com.rockpaperscissors.controller;

import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.helper.HandFactory;
import com.rockpaperscissors.model.OutputTemplate;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * GameController is a REST-Controller receiving requests to play the game.
 */
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameConfiguration gameConfiguration;

    /**
     * Receive incoming requests on "/play/{gameModeId}?hand={playersHand} and check parameter validity.
     * If all parameters are valid, calls GameService method to play a round of the game.
     *
     * @param gameModeId: The game mode to be played.
     * @param playersHand: The player's choice of hand.
     * @return Round: Returns a Round object specifying players' moves, winner and optional error message-
     */
    @RequestMapping(value = "/play/{gameModeId}", produces = "application/json")
    public @ResponseBody Round rockPaperScissors(@PathVariable int gameModeId, @RequestParam(value="hand", required = false) String playersHand) {
        Round round;
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
                    round.setMessage(OutputTemplate.ERROR_INVALID_CHOICE_COMPUTER);
                }
            } else {
                round = new Round(-1, Collections.emptyMap());
                round.setMessage(OutputTemplate.ERROR_INVALID_CHOICE_PLAYER);
            }
        } else {
            round = new Round(-1, Collections.emptyMap());
            round.setMessage(OutputTemplate.ERROR_INVALID_GAME_MODE);
        }
        return round;
    }
}
