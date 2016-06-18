package com.rockpaperscissors.controller;

import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

//TODO: how to send json data with angularjs https://hello-angularjs.appspot.com/angularjs-http-service-ajax-post-json-data-code-example
//Todo: send request

@RestController
@RequestMapping(value = "/play")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameConfiguration gameConfiguration;

    @RequestMapping(value = "/{gameModeId}/{playersChoice}", produces = "application/json")
    public Round rockPaperScissors(@PathVariable String playersChoice, @PathVariable int gameModeId) {
        Round round;
        if(GameConfiguration.isValidGameModeId(gameModeId)) {
            gameConfiguration.setGameMode(gameModeId);
            gameService.setGameModeAndStrategy(gameModeId);
        } else {
            round = new Round(-1, Collections.emptyMap(), "Invalid game mode.");
        }

        if(GameConfiguration.isValidHand(playersChoice)) {
            // Can potentially be empty
            String computersChoice = gameService.getRandomChoice();
            if(GameConfiguration.isValidHand(computersChoice)) {
                // Return an object of type Round to be displayed as JSON
                round = gameService.playRound(playersChoice, computersChoice);
            } else {
                round = new Round(-1, Collections.emptyMap(), "Computer's choice of hand was invalid.");
            }
        } else {
            round = new Round(-1, Collections.emptyMap(), "Player's choice of hand was invalid.");
        }
        return round;
    }
}
