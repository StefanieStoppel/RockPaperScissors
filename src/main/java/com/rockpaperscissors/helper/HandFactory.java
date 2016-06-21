package com.rockpaperscissors.helper;

import com.rockpaperscissors.config.GameConfiguration;

import java.util.concurrent.ThreadLocalRandom;

/**
 * HandFactory returns valid and invalid hands to be played in the game.
 *
 * @author Stefanie Stoppel
 */
public class HandFactory {

    private static final String[] invalidChoices = {"jfshf", "üfj(", "$(§$KE", "", "-4", "..", "papr", "0034358325"};

    private HandFactory(){}

    public static String getRandomInvalidHand() {
        return invalidChoices[ThreadLocalRandom.current().nextInt(0, invalidChoices.length)];
    }

    public static String getRandomValidHand() {
        String randomChoice = "";
        if(GameConfiguration.GAME_MODE == GameConfiguration.GAME_MODE_RPS) {
            randomChoice = GameConfiguration.ROCK_PAPER_SCISSORS[ThreadLocalRandom.current().nextInt(0,
                    GameConfiguration.ROCK_PAPER_SCISSORS.length)];
        } else if(GameConfiguration.GAME_MODE == GameConfiguration.GAME_MODE_RPSW) {
            randomChoice = GameConfiguration.ROCK_PAPER_SCISSORS_WELL[ThreadLocalRandom.current().nextInt(0,
                    GameConfiguration.ROCK_PAPER_SCISSORS_WELL.length)];
        }
        return randomChoice;
    }
}
