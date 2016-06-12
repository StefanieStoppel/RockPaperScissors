package com.rockpaperscissors.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.strategy.GameStrategy;
import com.rockpaperscissors.strategy.RockPaperScissorsStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//TODO: how to send json data with angularjs https://hello-angularjs.appspot.com/angularjs-http-service-ajax-post-json-data-code-example
//Todo: send request

@RestController
@RequestMapping(value = "/")
public class RoundController {

    private static final String PLAYER = "Player";
    private static final String COMPUTER = "Computer";

    private final AtomicLong counter = new AtomicLong();

    private GameStrategy gameStrategy = new RockPaperScissorsStrategy();

    @RequestMapping(value = "/rps", produces = "application/json")
    public Round rockPaperScissors(@RequestParam(value="object") String playersChoice) {
        // todo: choice of game strategy

        //todo: implement game exit on keypress other than r,p or s

        String computersChoice = getComputersChoice();

        // Map moves contains: "Player":"playersChoice", "Computer":"computersChoice"
        Map<String, String> moves = new LinkedHashMap<>();
        moves.put(PLAYER, playersChoice);
        moves.put(COMPUTER, computersChoice);

        int winnerId = -1;

        long roundCount = counter.incrementAndGet();

        int result = gameStrategy.determineWinner(playersChoice, computersChoice);
        if(result == 1) {
            winnerId = 0;
        } else if(result == -1) {
            winnerId = 1;
        }
        return new Round(roundCount, moves, winnerId);
    }

    private String getComputersChoice(){
        String[] rockPaperScissors = {"rock", "paper", "scissors"};
        int computersChoice = ThreadLocalRandom.current().nextInt(0, 3);
        return rockPaperScissors[computersChoice];
    }
}
