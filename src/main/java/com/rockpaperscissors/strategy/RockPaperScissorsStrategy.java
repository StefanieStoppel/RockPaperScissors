package com.rockpaperscissors.strategy;

import com.rockpaperscissors.config.GameConfiguration;
import org.apache.log4j.Logger;

public class RockPaperScissorsStrategy implements GameStrategy {

    private final static Logger logger = Logger.getLogger(RockPaperScissorsStrategy.class);

    @Override
    public boolean isValidChoice(String choice) {
        return choice.equals(GameConfiguration.ROCK) || choice.equals(GameConfiguration.PAPER) || choice.equals(GameConfiguration.SCISSORS);
    }

    @Override
    public int determineWinner(String playersChoice,
                               String computersChoice) {

        int result = Integer.MIN_VALUE;
        if(isValidChoice(playersChoice) && isValidChoice(computersChoice)) {
            if(playersChoice.equals(computersChoice)) {
                return 0;
            }
            switch (playersChoice) {
                case GameConfiguration.ROCK:
                    result = computersChoice.equals(GameConfiguration.SCISSORS) ?  1 : -1;
                    break;
                case GameConfiguration.PAPER:
                    result = computersChoice.equals(GameConfiguration.ROCK) ? 1 : -1;
                    break;
                case GameConfiguration.SCISSORS:
                    result = computersChoice.equals(GameConfiguration.PAPER) ? 1: -1;
                    break;
            }
        }
        logger.debug("playersChoice: " + playersChoice + ", computersChoice: "
                + computersChoice + ", result:" + result);
        return result;
    }
}
