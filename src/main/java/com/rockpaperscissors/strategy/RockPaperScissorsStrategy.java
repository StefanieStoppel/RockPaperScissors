package com.rockpaperscissors.strategy;

import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.model.OutputTemplate;
import org.apache.log4j.Logger;

public class RockPaperScissorsStrategy implements GameStrategy {

    private final static Logger logger = Logger.getLogger(RockPaperScissorsStrategy.class);

    @Override
    public boolean isValidChoice(String choice) {
        return choice.equals(GameConfiguration.ROCK) || choice.equals(GameConfiguration.PAPER) || choice.equals(GameConfiguration.SCISSORS);
    }

    @Override
    public String determineWinner(String playersChoice,
                               String computersChoice) {

        if(!isValidChoice(playersChoice)){
            logger.debug(OutputTemplate.ERROR_INVALID_CHOICE_PLAYER);
            return OutputTemplate.ERROR_INVALID_CHOICE_PLAYER;
        } else if(!isValidChoice(computersChoice)) {
            logger.debug(OutputTemplate.ERROR_INVALID_CHOICE_COMPUTER);
            return OutputTemplate.ERROR_INVALID_CHOICE_COMPUTER;
        } else {
            String result = "";
            if(playersChoice.equals(computersChoice)) {
                return result;
            }
            switch (playersChoice) {
                case GameConfiguration.ROCK:
                    result = computersChoice.equals(GameConfiguration.SCISSORS) ? GameConfiguration.PLAYER : GameConfiguration.COMPUTER;
                    break;
                case GameConfiguration.PAPER:
                    result = computersChoice.equals(GameConfiguration.ROCK) ? GameConfiguration.PLAYER : GameConfiguration.COMPUTER;
                    break;
                case GameConfiguration.SCISSORS:
                    result = computersChoice.equals(GameConfiguration.PAPER) ? GameConfiguration.PLAYER : GameConfiguration.COMPUTER;
                    break;
            }
            logger.debug("playersChoice: " + playersChoice + ", computersChoice: "
                    + computersChoice + ", result:" + result);
            return result;
        }
    }
}
