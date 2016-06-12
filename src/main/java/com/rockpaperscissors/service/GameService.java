package com.rockpaperscissors.service;


import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.strategy.GameStrategy;
import com.rockpaperscissors.strategy.RockPaperScissorsStrategy;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GameService {

    private static final Logger logger = Logger.getLogger(GameService.class);

    private static final String resultTemplate = "You played: %s \n The computer played: %s \n";
    private static final String winnerTemplate = "%s wins round %2d.";
    private static final String drawTemplate = "Round %2d is a draw.";
    private static final String errorTemplate = "Round %2d was invalid. Reason: %s";

    private static final String[] rockPaperScissors = {"rock", "paper", "scissors"};
    private static final String[] rockPaperScissorsWell = {"rock", "paper", "scissors", "well"};
    private String[] choices;

    private static final String PLAYER = "Player";
    private static final String COMPUTER = "Computer";

    private final AtomicLong roundCounter = new AtomicLong();
    private GameStrategy gameStrategy;

    // Result of this game round
    private Round round;

    private String playersChoice;
    private String computersChoice;
    private long roundCount;

    public GameService() {
    }

    public GameService(GameStrategy gameStrategy) {
        setGameStrategy(gameStrategy);
    }

    public void setGameStrategy(GameStrategy gameStrategy) throws IllegalArgumentException {
        this.gameStrategy = gameStrategy;
        setChoices();
    }

    private void setChoices() {
        if(gameStrategy != null) {
            if(gameStrategy instanceof RockPaperScissorsStrategy) {
                this.choices = rockPaperScissors;
            } else {
                this.choices = rockPaperScissorsWell;
            }
        } else {
            throw new NullPointerException();
        }
    }

    public Round getRound() {
        return round;
    }

    public void play(String playersChoice, String computersChoice) {
        // Set playersChoice
        this.playersChoice = playersChoice;
        this.computersChoice = computersChoice;

        // Map contains: "Player" -> playersChoice, "Computer" -> computersChoice
        Map<String, String> moves = new LinkedHashMap<>();
        moves.put(PLAYER, playersChoice);
        moves.put(COMPUTER, computersChoice);

        roundCount = roundCounter.incrementAndGet();

        String winner = getWinner(playersChoice, computersChoice);
        String output = createOutput(roundCount, playersChoice, computersChoice);

        round = new Round(roundCount,
                            moves,
                            winner,
                            output);
    }

    private String createOutput(long roundCount, String playersChoice, String computersChoice) {
        String output = "";
        if(!gameStrategy.isValidChoice(playersChoice)) {
            output += String.format(errorTemplate, roundCount, "Player's choice was invalid. Please choose a valid game move!");
        } else if(!gameStrategy.isValidChoice(computersChoice)) {
            output += String.format(errorTemplate, roundCount, "Computer's choice of game move was invalid.");
        } else {
            // Append info about who played what
            output += String.format(resultTemplate, playersChoice, computersChoice);

            String winner = getWinner(playersChoice, computersChoice);
            if(winner != null) {
                if(winner.length() > 0) {
                    output += String.format(winnerTemplate, winner, roundCount);
                } else {
                    output += String.format(drawTemplate, roundCount);
                }
            } else {
                //todo: handle null
            }
        }
        return output;
    }

    public String getWinner(String playersChoice, String computersChoice) {
        String winner = null;
        int result = gameStrategy.determineWinner(playersChoice, computersChoice);
        if(result == 1) {
            winner = PLAYER;
        } else if(result == -1) {
            winner = COMPUTER;
        } else if(result == 0) {
            winner = "";        //draw
        }
        return winner;
    }


    public String getRandomChoice() {
        if(choices != null) {
            return choices[ThreadLocalRandom.current().nextInt(0, choices.length)];
        } else {
            return "";
        }
    }

    public String getPlayersChoice() {
        return playersChoice;
    }

    public String getComputersChoice() {
        return computersChoice;
    }

    public long getRoundCount() {
        return roundCount;
    }
}
