package com.rockpaperscissors.config;

import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * This class holds game information such as the playable hands, game modes and player names.
 * It also checks played hands for validity.
 *
 * @author Stefanie Stoppel
 */
@Component
public class GameConfiguration {

    // Playable hands
    public static final String ROCK = "rock";
    public static final String PAPER = "paper";
    public static final String SCISSORS = "scissors";
    public static final String WELL = "well";

    // Arrays of playable hands
    public static final String[] ROCK_PAPER_SCISSORS = {ROCK, PAPER, SCISSORS};
    public static final String[] ROCK_PAPER_SCISSORS_WELL = {ROCK, PAPER, SCISSORS, WELL};

    // Player names
    public static final String PLAYER = "Player";
    public static final String COMPUTER = "Computer";

    // Game modes
    public static final int GAME_MODE_RPS = 0;
    public static final int GAME_MODE_RPSW = 1;
    public static int GAME_MODE = GAME_MODE_RPS; //default: 0 = rock, paper, scissors

    /**
     * Checks whether the passed hand is valid.
     *
     * @param hand the hand to be checked for validity
     * @return true if argument is valid hand, false otherwise
     * @throws IllegalArgumentException if the argument is null
     */
    public static boolean isValidHand(String hand) {
        if(hand == null){
            throw new IllegalArgumentException("isValidHand called with null argument.");
        }
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

    /**
     * Checks whether the passed gameModeId is valid.
     * @param gameModeId the game mode id to check
     * @return true if argument is valid gameModeId, false otherwise
     */
    public static boolean isValidGameModeId(int gameModeId) {
        return gameModeId == GameConfiguration.GAME_MODE_RPS || gameModeId == GameConfiguration.GAME_MODE_RPSW;
    }

    /**
     * Set the game mode by gameModeId.
     * @param gameModeId the gameModeId to set
     * @throws IllegalArgumentException if the game mode is not valid.
     */
    public void setGameMode(int gameModeId) {
        if(isValidGameModeId(gameModeId)) {
            GAME_MODE = gameModeId;
        } else {
            throw new IllegalArgumentException("Couldn't set game mode in configuration. Reason: Unknown game mode id: " + gameModeId);
        }
    }
}
