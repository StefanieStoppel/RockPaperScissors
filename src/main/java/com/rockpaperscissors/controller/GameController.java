package com.rockpaperscissors.controller;

import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.service.GameService;
import com.rockpaperscissors.strategy.GameStrategy;
import com.rockpaperscissors.strategy.RockPaperScissorsStrategy;
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

    @Autowired
    private GameStrategy gameStrategy;

    @RequestMapping(value = "/rps", produces = "application/json")
    public Round rockPaperScissors(@RequestParam(value="object") String playersChoice) {

        gameStrategy = new RockPaperScissorsStrategy();
        gameService.setGameStrategy(gameStrategy);
        gameService.play(playersChoice);

        return gameService.getRound();
    }
}
