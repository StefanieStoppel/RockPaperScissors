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

    private GameStrategy gameStrategy;

    public GameService() {}

    public void setStrategyByGameMode(int gameMode) throws IllegalArgumentException {
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

        Round round = new Round(roundCounter.incrementAndGet(), moves);

        String winner = gameStrategy.determineWinner(round, playersChoice, computersChoice);
        round.setWinner(winner);

        return round;
    }

    public long getRoundCount() {
        return roundCounter.get();
    }
}
