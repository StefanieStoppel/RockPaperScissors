package com.rockpaperscissors.strategy;

/**
 * @author Stefanie Stoppel
 */

import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.model.OutputTemplate;
import com.rockpaperscissors.model.Round;
import org.apache.log4j.Logger;

public class RockPaperScissorsWellStrategy implements GameStrategy {

    private final static Logger logger = Logger.getLogger(RockPaperScissorsStrategy.class);

    @Override
    public boolean isValidChoice(String choice) {
        return choice.equals(GameConfiguration.ROCK)
                || choice.equals(GameConfiguration.PAPER)
                || choice.equals(GameConfiguration.SCISSORS)
                || choice.equals(GameConfiguration.WELL);
    }

    @Override
    public String determineWinner(Round round, String playersChoice,
                                  final String computersChoice) {
        String winner = "";
        if(!isValidChoice(playersChoice)){
            logger.debug(OutputTemplate.ERROR_INVALID_HAND_PLAYER);
            round.setMessage(OutputTemplate.ERROR_INVALID_HAND_PLAYER);
        } else if(!isValidChoice(computersChoice)) {
            logger.debug(OutputTemplate.ERROR_INVALID_HAND_COMPUTER);
            round.setMessage(OutputTemplate.ERROR_INVALID_HAND_COMPUTER);
        } else if(!playersChoice.equals(computersChoice)){
            switch (playersChoice) {
                case GameConfiguration.ROCK:
                    winner = computersChoice.equals(GameConfiguration.SCISSORS) ? GameConfiguration.PLAYER : GameConfiguration.COMPUTER;
                    break;
                case GameConfiguration.PAPER:
                    winner = (computersChoice.equals(GameConfiguration.ROCK) || computersChoice.equals(GameConfiguration.WELL))
                                ? GameConfiguration.PLAYER : GameConfiguration.COMPUTER;
                    break;
                case GameConfiguration.SCISSORS:
                    winner = computersChoice.equals(GameConfiguration.PAPER) ? GameConfiguration.PLAYER : GameConfiguration.COMPUTER;
                    break;
                case GameConfiguration.WELL:
                    winner = computersChoice.equals(GameConfiguration.PAPER) ? GameConfiguration.COMPUTER : GameConfiguration.PLAYER;
                    break;
            }
            logger.debug("playersChoice: " + playersChoice + ", computersChoice: "
                    + computersChoice + ", result:" + winner);
        }
        return winner;
    }
}

