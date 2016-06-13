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

    private final AtomicLong roundCounter = new AtomicLong();
    private long roundCount;

    private GameStrategy gameStrategy;
    private int gameMode;

    private String winner;

    public GameService() {}

    public void setGameModeAndStrategy(int gameMode) throws IllegalArgumentException {
        this.gameMode = gameMode;
        if(gameMode == GameConfiguration.GAME_MODE_RPS) {
            this.gameStrategy = new RockPaperScissorsStrategy();
        } else if(gameMode == GameConfiguration.GAME_MODE_RPSW) {
//            this.gameStrategy = new RockPaperScissorsWellStrategy(); todo: implement
        }
    }

    public Round playRound(String playersChoice, String computersChoice) {

        // Map contains: "Player" -> playersChoice, "Computer" -> computersChoice
        Map<String, String> moves = new LinkedHashMap<>();
        moves.put(GameConfiguration.PLAYER, playersChoice);
        moves.put(GameConfiguration.COMPUTER, computersChoice);

        incrementRoundCounter();

        winner = determineWinner(playersChoice, computersChoice);

        return new Round(roundCount, moves, winner);
    }

    public void incrementRoundCounter() {
        roundCount = roundCounter.incrementAndGet();
    }

    public String determineWinner(String playersChoice, String computersChoice) {
        return gameStrategy.determineWinner(playersChoice, computersChoice);
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

    public long getRoundCount() {
        return roundCount;
    }
}
