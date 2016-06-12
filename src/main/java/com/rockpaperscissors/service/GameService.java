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

    private static final String[] rockPaperScissors = {"rock", "paper", "scissors"};
    private static final String[] rockPaperScissorsWell = {"rock", "paper", "scissors", "well"};
    private String[] choices;

    private static final String PLAYER = "Player";
    private static final String COMPUTER = "Computer";

    private final AtomicLong roundCounter = new AtomicLong();
    private GameStrategy gameStrategy;

    // Result of this game round
    private Round round;


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

    public void play(String playersChoice) {
        // Get random choice as computer's choice
        String computersChoice = getComputersChoice();
        if(computersChoice.isEmpty()) {
            round = new Round(roundCounter.incrementAndGet(),
                                new LinkedHashMap<>(),
                                Integer.MIN_VALUE);
            return;
        }

        // Map contains: "Player" -> playersChoice, "Computer" -> computersChoice
        Map<String, String> moves = new LinkedHashMap<>();
        moves.put(PLAYER, playersChoice);
        moves.put(COMPUTER, computersChoice);

        round = new Round(roundCounter.incrementAndGet(),
                            moves,
                            getWinnerIdx(playersChoice, computersChoice));
    }

    private int getWinnerIdx(String playersChoice, String computersChoice)  {
        int winnerIdx = -1;
        if(gameStrategy != null) {
            int result = gameStrategy.determineWinner(playersChoice, computersChoice);
            if(result == 1) {
                winnerIdx = 0;
            } else if(result == -1) {
                winnerIdx = 1;
            }
        } else {
            //todo: handle nullpointer
            logger.error("GameService: No GameStrategy was set.");
        }
        return winnerIdx;
    }

    private String getComputersChoice() {
        if(choices != null) {
            return choices[ThreadLocalRandom.current().nextInt(0, choices.length)];
        } else {
            return "";
        }
    }
}
