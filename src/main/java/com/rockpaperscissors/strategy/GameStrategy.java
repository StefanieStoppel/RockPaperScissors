package com.rockpaperscissors.strategy;

import com.rockpaperscissors.model.Round;

/**
 * This interface defines the architecture for different game strategies in
 * "Rock Paper Scissors".
 */
public interface GameStrategy {

    boolean isValidChoice(String choice);

    /**
     * Determines the winner of this round by passed arguments.
     * @param playersChoice: player's choice of game move
     * @param computersChoice: computer's choice of game move
     * @return "Player":        Player won.
     *          "Computer":     Computer won.
     *          "":             It was a draw or winner couldn't be determined because of an error.
     */
    String determineWinner(Round round, String playersChoice,
                           String computersChoice);
}
