package com.rockpaperscissors.strategy;

/**
 * This interface defines the architecture for different game strategies in
 * "Rock Paper Scissors".
 */
public interface GameStrategy {

    /**
     * Determines which of the passed arguments wins.
     * @param playersChoice: player's choice of game move
     * @param computersChoice: computer's choice of game move
     * @return 1:   playersChoice wins,
     *         -1:  computersChoice wins,
     *         0:   It's a draw.
     */
    int determineWinner(String playersChoice,
                                   String computersChoice);
}
