package com.rockpaperscissors.controller;

import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//TODO: how to send json data with angularjs https://hello-angularjs.appspot.com/angularjs-http-service-ajax-post-json-data-code-example
//Todo: send request

@RestController
@RequestMapping(value = "/")
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/rps", produces = "application/json")
    public Round rockPaperScissors(@RequestParam(value="object") String playersChoice) {

        gameService.setGameModeAndStrategy(GameConfiguration.GAME_MODE_RPS);

        // Can potentially be empty
        String computersChoice = gameService.getRandomChoice();

        // Return an object of type Round to be displayed as JSON
        return gameService.play(playersChoice, computersChoice);
    }
}
