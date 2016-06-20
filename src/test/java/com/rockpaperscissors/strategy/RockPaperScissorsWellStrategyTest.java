package com.rockpaperscissors.strategy;

import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.model.Round;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test class for RockPaperScissorsWellStrategy.
 *
 * @author Stefanie Stoppel
 */
public class RockPaperScissorsWellStrategyTest {

    private RockPaperScissorsWellStrategy rockPaperScissorsWellStrategy;
    private Map<String, String> moves;

    @Before
    public void setUp() {
        rockPaperScissorsWellStrategy = new RockPaperScissorsWellStrategy();
    }

    @Test
    public void testIsValidChoice() {
        assertTrue(rockPaperScissorsWellStrategy.isValidChoice(GameConfiguration.ROCK));
        assertTrue(rockPaperScissorsWellStrategy.isValidChoice(GameConfiguration.SCISSORS));
        assertTrue(rockPaperScissorsWellStrategy.isValidChoice(GameConfiguration.PAPER));
        assertTrue(rockPaperScissorsWellStrategy.isValidChoice(GameConfiguration.WELL));
    }

    @Test
    public void testIsInvalidChoice() {
        assertFalse(rockPaperScissorsWellStrategy.isValidChoice(""));
        assertFalse(rockPaperScissorsWellStrategy.isValidChoice("foo"));
        assertFalse(rockPaperScissorsWellStrategy.isValidChoice("-100"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsInvalidChoice_IllegalArgumentException() {
        rockPaperScissorsWellStrategy.isValidChoice(null);
    }

    @Test
    public void testDetermineWinnerComputer() {
        setUpMoves(GameConfiguration.ROCK, GameConfiguration.WELL);
        Round round = new Round(1, moves);

        String winner = rockPaperScissorsWellStrategy.determineWinner(round, GameConfiguration.ROCK, GameConfiguration.WELL);
        assertEquals(GameConfiguration.COMPUTER, winner);
    }

    @Test
    public void testDetermineWinnerPlayer() {
        setUpMoves(GameConfiguration.ROCK, GameConfiguration.SCISSORS);
        Round round = new Round(1, moves);

        String winner = rockPaperScissorsWellStrategy.determineWinner(round, GameConfiguration.ROCK, GameConfiguration.SCISSORS);
        assertEquals(GameConfiguration.PLAYER, winner);
    }

    @Test
    public void testDetermineWinnerDraw() {
        setUpMoves(GameConfiguration.WELL, GameConfiguration.WELL);
        Round round = new Round(1, moves);

        String winner = rockPaperScissorsWellStrategy.determineWinner(round, GameConfiguration.WELL, GameConfiguration.WELL);
        assertEquals(Strings.EMPTY, winner);
    }

    private void setUpMoves(String playersChoice, String computersChoice) {
        moves = new LinkedHashMap<>();
        moves.put(GameConfiguration.PLAYER, playersChoice);
        moves.put(GameConfiguration.COMPUTER, computersChoice);
    }
}