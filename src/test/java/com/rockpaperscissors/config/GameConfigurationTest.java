package com.rockpaperscissors.config;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is a test class for class GameConfiguration.
 *
 * @author Stefanie Stoppel
 */
public class GameConfigurationTest {

    private GameConfiguration gameConfiguration;
    private String[] validHands;
    private String[] invalidHands;

    @Before
    public void setUp(){
        gameConfiguration = new GameConfiguration();
        validHands = new String[]{"rock", "paper", "scissors"};
        invalidHands = new String[]{"abc", "42", "foo", "&$Z$%§(§*", ""};
    }

    @Test
    public void testValidHandGameMode0() {
        // Test hands for game mode 0
        gameConfiguration.setGameMode(0);

        // Test valid hands
        assertTrue(GameConfiguration.isValidHand(validHands[0]));
        assertTrue(GameConfiguration.isValidHand(validHands[1]));
        assertTrue(GameConfiguration.isValidHand(validHands[2]));
    }

    @Test
    public void testInvalidHandGameMode0() {
        // Test invalid hands
        gameConfiguration.setGameMode(0);
        assertEquals(0, GameConfiguration.GAME_MODE);

        assertFalse(GameConfiguration.isValidHand("well"));
        assertFalse(GameConfiguration.isValidHand(invalidHands[0]));
        assertFalse(GameConfiguration.isValidHand(invalidHands[1]));
        assertFalse(GameConfiguration.isValidHand(invalidHands[2]));
        assertFalse(GameConfiguration.isValidHand(invalidHands[3]));
        assertFalse(GameConfiguration.isValidHand(invalidHands[4]));
    }

    @Test
    public void testValidHandGameMode1() {
        // Test for game mode 1
        gameConfiguration.setGameMode(1);

        // Test valid hands
        assertTrue(GameConfiguration.isValidHand(validHands[0]));
        assertTrue(GameConfiguration.isValidHand(validHands[1]));
        assertTrue(GameConfiguration.isValidHand(validHands[2]));
        assertTrue(GameConfiguration.isValidHand("well"));
    }

    @Test
    public void testInvalidHandGameMode1() {
        // Test invalid hands
        gameConfiguration.setGameMode(1);

        assertFalse(GameConfiguration.isValidHand(invalidHands[0]));
        assertFalse(GameConfiguration.isValidHand(invalidHands[1]));
        assertFalse(GameConfiguration.isValidHand(invalidHands[2]));
        assertFalse(GameConfiguration.isValidHand(invalidHands[3]));
        assertFalse(GameConfiguration.isValidHand(invalidHands[4]));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHand_IllegalArgumentException() {
        // Test IllegalArgumentException
        GameConfiguration.isValidHand(null);
    }

    @Test
    public void testValidGameModeId() {
        // Test valid ids
        assertTrue(GameConfiguration.isValidGameModeId(0));
        assertTrue(GameConfiguration.isValidGameModeId(1));

        // Test invalid ids
        assertFalse(GameConfiguration.isValidGameModeId(-1));
        assertFalse(GameConfiguration.isValidGameModeId(42));
        assertFalse(GameConfiguration.isValidGameModeId(Integer.MAX_VALUE));
        assertFalse(GameConfiguration.isValidGameModeId(Integer.MIN_VALUE));
    }

    @Test
    public void testSetValidGameMode() {
        // Test setting valid game modes
        gameConfiguration.setGameMode(0);
        assertEquals(0, GameConfiguration.GAME_MODE);

        gameConfiguration.setGameMode(1);
        assertEquals(1, GameConfiguration.GAME_MODE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetInvalidGameMode_IllegalArgumentException() {
        gameConfiguration.setGameMode(42);
    }
}