package com.rockpaperscissors;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import com.rockpaperscissors.strategy.GameStrategy;
import com.rockpaperscissors.strategy.RockPaperScissorsStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//TODO: how to send json data with angularjs https://hello-angularjs.appspot.com/angularjs-http-service-ajax-post-json-data-code-example
//Todo: send request

@RestController
@RequestMapping(value = "/move")
public class MoveController {

    private static final String resultTemplate = "You played: %s \n The computer played: %s \n";
    private static final String winnerTemplate = "%s wins round %2d.";
    private static final String drawTemplate = "Round %2d is a draw.";
    private static final String PLAYER = "Player";
    private static final String COMPUTER = "Computer";

    private final AtomicLong counter = new AtomicLong();

    private GameStrategy gameStrategy = new RockPaperScissorsStrategy();

    @RequestMapping(value = "/rps", produces = "application/json")
    public Move rockPaperScissors(@RequestParam(value="object") String playersChoice) {
        // todo: choice of game strategy

        //todo: implement game exit on keypress other than r,p or s

        String computersChoice = getComputersChoice();

        String output = String.format(resultTemplate, playersChoice, computersChoice);
        long round = counter.incrementAndGet();

        int result = gameStrategy.determineWinner(playersChoice, computersChoice);
        if(result == 1) {
            output += String.format(winnerTemplate, PLAYER, round);
        } else if(result == -1) {
            output += String.format(winnerTemplate, COMPUTER, round);
        } else {
            output += String.format(drawTemplate, round);
        }
        return new Move(round, output);
    }

    private String getComputersChoice(){
        String[] rockPaperScissors = {"rock", "paper", "scissors"};
        int computersChoice = ThreadLocalRandom.current().nextInt(0, 3);
        return rockPaperScissors[computersChoice];
    }
}
