package com.rockpaperscissors.service;


import com.rockpaperscissors.config.GameConfiguration;
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

    // todo: Put these in a separate class
    private static final String resultTemplate = "You played: %s \n The computer played: %s \n";
    private static final String winnerTemplate = "%s wins round %2d.";
    private static final String drawTemplate = "Round %2d is a draw.";
    private static final String errorTemplate = "Round %2d was invalid. Reason: %s";

    private String[] choices;

    private final AtomicLong roundCounter = new AtomicLong();
    private GameStrategy gameStrategy;

    // Result of this game round, will be returned to GameController
    private Round round;

    private String playersChoice;
    private String computersChoice;
    private long roundCount;
    private int gameMode;
    private String winner;

    public GameService() {
    }

/*
    public GameService(GameStrategy gameStrategy) {
        setGameModeAndStrategy(gameStrategy);
    }
*/

    public void setGameModeAndStrategy(int gameMode) throws IllegalArgumentException {
        this.gameMode = gameMode;
        if(gameMode == GameConfiguration.GAME_MODE_RPS) {
            this.gameStrategy = new RockPaperScissorsStrategy();
        } else if(gameMode == GameConfiguration.GAME_MODE_RPSW) {
//            this.gameStrategy = new RockPaperScissorsWellStrategy(); todo: implement
        }
    }

    public Round getRound() {
        return round;
    }

    public Round play(String playersChoice, String computersChoice) {
        // Set playersChoice
        this.playersChoice = playersChoice;
        this.computersChoice = computersChoice;

        // Map contains: "Player" -> playersChoice, "Computer" -> computersChoice
        Map<String, String> moves = new LinkedHashMap<>();
        moves.put(GameConfiguration.PLAYER, playersChoice);
        moves.put(GameConfiguration.COMPUTER, computersChoice);

        roundCount = roundCounter.incrementAndGet();

        winner = getWinner(playersChoice, computersChoice);
        String output = createOutput(roundCount, playersChoice, computersChoice);

        round = new Round(roundCount,
                            moves,
                            winner,
                            output);
        return round;
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

    //todo: separate determineWinner and getWinner -> getWinner should be pure getter

    public String getWinner(String playersChoice, String computersChoice) {
        String winner = null;
        int result = gameStrategy.determineWinner(playersChoice, computersChoice);
        if(result == 1) {
            winner = GameConfiguration.PLAYER;
        } else if(result == -1) {
            winner = GameConfiguration.COMPUTER;
        } else if(result == 0) {
            winner = "";        //draw
        }
        return winner;
    }


    public String getRandomChoice() {
        String randomChoice = "";
        if(gameMode == GameConfiguration.GAME_MODE_RPS) {
            randomChoice = GameConfiguration.ROCK_PAPER_SCISSORS[ThreadLocalRandom.current().nextInt(0,
                    GameConfiguration.ROCK_PAPER_SCISSORS.length)];
        } else if(gameMode == GameConfiguration.GAME_MODE_RPSW) {
            randomChoice = GameConfiguration.ROCK_PAPER_SCISSORS_WELL[ThreadLocalRandom.current().nextInt(0,
                    GameConfiguration.ROCK_PAPER_SCISSORS_WELL.length)];
        }
        return randomChoice;
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
