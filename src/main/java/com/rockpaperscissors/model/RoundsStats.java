package com.rockpaperscissors.model;

/**
 * RoundStats represents the user input in the form of game mode and played hand.
 * The JSON object passed to GameController by the frontend is converted to a RoundStats Java object.
 *
 * @author Stefanie Stoppel
 */
public class RoundsStats {
    private int gameMode;
    private String playersHand;

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public String getPlayersHand() {
        return playersHand;
    }

    public void setPlayersHand(String playersHand) {
        if(playersHand == null) {
            throw new IllegalArgumentException("setPlayersHand() called with argument null.");
        }
        this.playersHand = playersHand;
    }
}
