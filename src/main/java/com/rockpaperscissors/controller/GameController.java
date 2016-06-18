package com.rockpaperscissors.controller;

import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.helper.HandFactory;
import com.rockpaperscissors.model.OutputTemplate;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

//TODO: how to send json data with angularjs https://hello-angularjs.appspot.com/angularjs-http-service-ajax-post-json-data-code-example
//Todo: send request

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameConfiguration gameConfiguration;

    @RequestMapping(value = "/")
    public String index(ModelMap map){
        return "index";
    }

    @RequestMapping(value = "/play/{gameModeId}", produces = "application/json")
    public Round rockPaperScissors(@PathVariable int gameModeId, @RequestParam(value="hand", required = false) String playersHand) {
        Round round;
        if(GameConfiguration.isValidGameModeId(gameModeId)) {
            gameConfiguration.setGameMode(gameModeId);
            gameService.setGameModeAndStrategy(gameModeId);
        } else {
            return new Round(-1, Collections.emptyMap(), OutputTemplate.ERROR_INVALID_GAME_MODE);
        }

        if(GameConfiguration.isValidHand(playersHand)) {
            // Can potentially be empty
            String computersChoice = HandFactory.getRandomValidHand(gameModeId);
            if(GameConfiguration.isValidHand(computersChoice)) {
                // Return an object of type Round to be displayed as JSON
                round = gameService.playRound(playersHand, computersChoice);
            } else {
                round = new Round(-1, Collections.emptyMap(), OutputTemplate.ERROR_INVALID_CHOICE_COMPUTER);
            }
        } else {
            round = new Round(-1, Collections.emptyMap(), OutputTemplate.ERROR_INVALID_CHOICE_PLAYER);
        }
        return round;
    }
}
