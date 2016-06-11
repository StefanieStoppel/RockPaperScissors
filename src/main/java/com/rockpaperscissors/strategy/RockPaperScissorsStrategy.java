package com.rockpaperscissors.strategy;

import org.apache.log4j.Logger;

public class RockPaperScissorsStrategy implements GameStrategy {

    private final static Logger logger = Logger.getLogger(RockPaperScissorsStrategy.class);

    private static final String ROCK = "rock";
    private static final String PAPER = "paper";
    private static final String SCISSORS = "scissors";

    @Override
    public int determineWinner(String playersChoice,
                                          String computersChoice) throws NullPointerException{
        //todo is this ok? -> no, we need to deal with non-existing objects passed as params
        int result = 0;
        if(playersChoice.equals(computersChoice)) {
            return result;
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
        return result;
    }
}
