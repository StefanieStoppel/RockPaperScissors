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

/**
 * This service plays a round of the chosen game mode when playRound() is called.
 * It is responsible for choosing a game strategy depending on the
 * game mode chosen by the user.
 *
 */
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
        } else {
            throw new IllegalArgumentException("setStrategyByGameMode() called with illegal argument gameMode = " + gameMode);
        }
    }

    public Round playRound(String playersChoice, String computersChoice) {
        String illegalArg = playersChoice == null ? "playersHand=null"
                : (computersChoice == null ? "computersHand=null" : "");
        if(!illegalArg.isEmpty()) {
            throw new IllegalArgumentException("playRound() called with illegal argument: " + illegalArg);
        }
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

    public GameStrategy getGameStrategy() {
        return gameStrategy;
    }
}
