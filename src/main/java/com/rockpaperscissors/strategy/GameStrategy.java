package com.rockpaperscissors.strategy;

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
     *          "":             It was a draw.
     *          Error message:  One of the choices was invalid.
     */
    String determineWinner(String playersChoice,
                                   String computersChoice);
}
