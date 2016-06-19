package com.rockpaperscissors.controller;

import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.helper.HandFactory;
import com.rockpaperscissors.model.OutputTemplate;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

//TODO: how to send json data with angularjs https://hello-angularjs.appspot.com/angularjs-http-service-ajax-post-json-data-code-example
//Todo: send request

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameConfiguration gameConfiguration;

    @RequestMapping(value = "/play/{gameModeId}", produces = "application/json")
    public @ResponseBody Round rockPaperScissors(@PathVariable int gameModeId, @RequestParam(value="hand", required = false) String playersHand) {
        Round round;
        if(GameConfiguration.isValidGameModeId(gameModeId)) {
            gameConfiguration.setGameMode(gameModeId);
            gameService.setGameModeAndStrategy(gameModeId);
        } else {
            return new Round(-1, Collections.emptyMap(), OutputTemplate.ERROR_INVALID_GAME_MODE);
        }

        if(GameConfiguration.isValidHand(playersHand)) {
            // Can potentially be empty
            String computersHand = HandFactory.getRandomValidHand(gameModeId);
            if(GameConfiguration.isValidHand(computersHand)) {
                // Return an object of type Round to be displayed as JSON
                round = gameService.playRound(playersHand, computersHand);
            } else {
                round = new Round(-1, Collections.emptyMap(), OutputTemplate.ERROR_INVALID_CHOICE_COMPUTER);
            }
        } else {
            round = new Round(-1, Collections.emptyMap(), OutputTemplate.ERROR_INVALID_CHOICE_PLAYER);
        }
        return round;
    }
}
