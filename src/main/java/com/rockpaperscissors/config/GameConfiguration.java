package com.rockpaperscissors.config;

import java.util.Arrays;

public final class GameConfiguration {

    public static final String ROCK = "rock";
    public static final String PAPER = "paper";
    public static final String SCISSORS = "scissors";
    public static final String WELL = "well";

    public static final String[] ROCK_PAPER_SCISSORS = {ROCK, PAPER, SCISSORS};
    public static final String[] ROCK_PAPER_SCISSORS_WELL = {ROCK, PAPER, SCISSORS, WELL};

    public static final String PLAYER = "Player";
    public static final String COMPUTER = "Computer";

    public static final int GAME_MODE_RPS = 0;
    public static final int GAME_MODE_RPSW = 1;
    public static int GAME_MODE = GAME_MODE_RPS; //default: 0 = rock, paper, scissors


    public static boolean isValidHand(String hand) {
        String[] haystack;
        if(GAME_MODE == 0) {
            if(hand.equals(WELL)) {
                return false;
            }
            haystack = ROCK_PAPER_SCISSORS;
        } else {
            haystack = ROCK_PAPER_SCISSORS_WELL;
        }
        return Arrays.asList(haystack).contains(hand);
    }

    public void setGameMode(int gameMode) {
        //todo: check for validity
        GAME_MODE = gameMode;
    }
}
