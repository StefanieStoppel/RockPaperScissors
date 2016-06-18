package com.rockpaperscissors.service;


import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.strategy.GameStrategy;
import com.rockpaperscissors.strategy.RockPaperScissorsStrategy;
import com.rockpaperscissors.strategy.RockPaperScissorsWellStrategy;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GameService {

    private static final Logger logger = Logger.getLogger(GameService.class);

    private final AtomicLong roundCounter = new AtomicLong();
    private long roundCount;

    private GameStrategy gameStrategy;
    private int gameMode;

    public GameService() {}

    public void setGameModeAndStrategy(int gameMode) throws IllegalArgumentException {
        this.gameMode = gameMode;
        if(gameMode == GameConfiguration.GAME_MODE_RPS) {
            this.gameStrategy = new RockPaperScissorsStrategy();
        } else if(gameMode == GameConfiguration.GAME_MODE_RPSW) {
            this.gameStrategy = new RockPaperScissorsWellStrategy();
        }
    }

    public Round playRound(String playersChoice, String computersChoice) {
        // Map contains: "Player" -> playersChoice, "Computer" -> computersChoice
        Map<String, String> moves = new LinkedHashMap<>();
        moves.put(GameConfiguration.PLAYER, playersChoice);
        moves.put(GameConfiguration.COMPUTER, computersChoice);

        incrementRoundCounter();

        String winner = gameStrategy.determineWinner(playersChoice, computersChoice);

        return new Round(roundCount, moves, winner);
    }

    private void incrementRoundCounter() {
        roundCount = roundCounter.incrementAndGet();
    }

    public long getRoundCount() {
        return roundCount;
    }
}
