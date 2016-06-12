package com.rockpaperscissors.strategy;

import org.apache.log4j.Logger;

public class RockPaperScissorsStrategy implements GameStrategy {

    private final static Logger logger = Logger.getLogger(RockPaperScissorsStrategy.class);

    private static final String ROCK = "rock";
    private static final String PAPER = "paper";
    private static final String SCISSORS = "scissors";

    @Override
    public boolean isValidChoice(String choice) {
        return choice.equals(ROCK) || choice.equals(PAPER) || choice.equals(SCISSORS);
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
                case ROCK:
                    result = computersChoice.equals(SCISSORS) ?  1 : -1;
                    break;
                case PAPER:
                    result = computersChoice.equals(ROCK) ? 1 : -1;
                    break;
                case SCISSORS:
                    result = computersChoice.equals(PAPER) ? 1: -1;
                    break;
            }
        }
        return result;
    }
}
