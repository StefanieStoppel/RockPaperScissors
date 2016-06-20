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
 * Test class for RockPaperScissorsStrategy.
 *
 * @author Stefanie Stoppel
 */
public class RockPaperScissorsStrategyTest {

    private RockPaperScissorsStrategy rockPaperScissorsStrategy;
    private Map<String, String> moves;

    @Before
    public void setUp() {
        rockPaperScissorsStrategy = new RockPaperScissorsStrategy();
    }

    @Test
    public void testIsValidChoice() {
        assertTrue(rockPaperScissorsStrategy.isValidChoice(GameConfiguration.ROCK));
        assertTrue(rockPaperScissorsStrategy.isValidChoice(GameConfiguration.SCISSORS));
        assertTrue(rockPaperScissorsStrategy.isValidChoice(GameConfiguration.PAPER));
    }


    @Test
    public void testIsInvalidChoice() {
        assertFalse(rockPaperScissorsStrategy.isValidChoice(GameConfiguration.WELL));
        assertFalse(rockPaperScissorsStrategy.isValidChoice("foo"));
        assertFalse(rockPaperScissorsStrategy.isValidChoice("-100"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsInvalidChoice_IllegalArgumentException() {
        rockPaperScissorsStrategy.isValidChoice(null);
    }

    @Test
    public void testDetermineWinnerComputer() {
        setUpMoves(GameConfiguration.ROCK, GameConfiguration.PAPER);
        Round round = new Round(1, moves);

        String winner = rockPaperScissorsStrategy.determineWinner(round, GameConfiguration.ROCK, GameConfiguration.PAPER);
        assertEquals(GameConfiguration.COMPUTER, winner);
    }

    @Test
    public void testDetermineWinnerPlayer() {
        setUpMoves(GameConfiguration.SCISSORS, GameConfiguration.PAPER);
        Round round = new Round(1, moves);

        String winner = rockPaperScissorsStrategy.determineWinner(round, GameConfiguration.SCISSORS, GameConfiguration.PAPER);
        assertEquals(GameConfiguration.PLAYER, winner);
    }

    @Test
    public void testDetermineWinnerDraw() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.PAPER);
        Round round = new Round(1, moves);

        String winner = rockPaperScissorsStrategy.determineWinner(round, GameConfiguration.PAPER, GameConfiguration.PAPER);
        assertEquals(Strings.EMPTY, winner);
    }

    private void setUpMoves(String playersChoice, String computersChoice) {
        moves = new LinkedHashMap<>();
        moves.put(GameConfiguration.PLAYER, playersChoice);
        moves.put(GameConfiguration.COMPUTER, computersChoice);
    }


}