package com.rockpaperscissors.helper;

import com.rockpaperscissors.config.GameConfiguration;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for HandFactory.
 *
 * @author Stefanie Stoppel
 */

/**
 * Todo: Look into parametrized tests and add that here if applicable.
 */
public class HandFactoryTest {

    private static final String[] invalidChoices = {"jfshf", "üfj(", "$(§$KE", "", "-4", "..", "papr", "0034358325"};

    @Test
    public void testGetRandomInvalidHand() {
        String invalidHand = HandFactory.getRandomInvalidHand();

        assertFalse(GameConfiguration.isValidHand(invalidHand));
        assertTrue(Arrays.asList(invalidChoices).contains(invalidHand));
    }

    @Test
    public void testGetRandomValidHand() {
        String validHand = HandFactory.getRandomValidHand();

        assertTrue(GameConfiguration.isValidHand(validHand));
    }

}