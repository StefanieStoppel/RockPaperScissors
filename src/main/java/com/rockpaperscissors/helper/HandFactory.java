package com.rockpaperscissors.helper;

import com.rockpaperscissors.config.GameConfiguration;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Stefanie Stoppel
 */
@Component
public class HandFactory {

    private static final String[] invalidChoices = {"jfshf", "üfj(", "$(§$KE", "", "-4", "..", "papr", "0034358325"};

    public static String getRandomInvalidChoice() {
        return invalidChoices[ThreadLocalRandom.current().nextInt(0, invalidChoices.length)];
    }

    public String getRandomValidChoice(int gameMode) {
        String randomChoice = "";
        if(gameMode == GameConfiguration.GAME_MODE_RPS) {
            randomChoice = GameConfiguration.ROCK_PAPER_SCISSORS[ThreadLocalRandom.current().nextInt(0,
                    GameConfiguration.ROCK_PAPER_SCISSORS.length)];
        } else if(gameMode == GameConfiguration.GAME_MODE_RPSW) {
            randomChoice = GameConfiguration.ROCK_PAPER_SCISSORS_WELL[ThreadLocalRandom.current().nextInt(0,
                    GameConfiguration.ROCK_PAPER_SCISSORS_WELL.length)];
        }
        return randomChoice;
    }
}
