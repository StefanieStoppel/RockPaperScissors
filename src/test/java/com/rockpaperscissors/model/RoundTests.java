package com.rockpaperscissors.model;

import com.rockpaperscissors.RockPaperScissorsApplication;
import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.service.GameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RockPaperScissorsApplication.class)
public class RoundTests {

    @Autowired
    GameService gameService;

    private long roundCount;

    private final AtomicLong roundCounter = new AtomicLong();
    private Map<String, String> moves;

    @Before
    public void setUp() throws Exception {
        // Play game mode rock, paper, scissors
        gameService.setStrategyByGameMode(GameConfiguration.GAME_MODE_RPS);
    }

    public void setUpMoves(String playersChoice, String computersChoice) {
        moves = new LinkedHashMap<>();
        moves.put(GameConfiguration.PLAYER, playersChoice);
        moves.put(GameConfiguration.COMPUTER, computersChoice);
    }

    @Test
    public void testRoundFirstConstructor() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.ROCK);

        Round round = new Round(1, moves);

        Assert.assertEquals(1, round.getCount());
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals("", round.getWinner());
        Assert.assertEquals("", round.getMessage());
    }

    @Test
    public void testRoundSecondConstructor() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.SCISSORS);

        Round round = new Round(42, moves, GameConfiguration.COMPUTER);

        Assert.assertEquals(42, round.getCount());
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals(GameConfiguration.COMPUTER, round.getWinner());
        Assert.assertEquals("", round.getMessage());
    }

    @Test
    public void testSetRoundWinner() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.SCISSORS);
        Round round = new Round(42, moves, GameConfiguration.COMPUTER);
        round.setWinner(GameConfiguration.COMPUTER);

        Assert.assertEquals(GameConfiguration.COMPUTER, round.getWinner());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRoundWinner_IllegalArgumentException() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.SCISSORS);
        Round round = new Round(42, moves, GameConfiguration.COMPUTER);
        round.setWinner(null);
    }

    @Test
    public void testSetRoundMessage() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.SCISSORS);
        Round round = new Round(42, moves, GameConfiguration.COMPUTER);
        round.setMessage(OutputTemplate.ERROR_INVALID_HAND_COMPUTER);

        Assert.assertEquals(OutputTemplate.ERROR_INVALID_HAND_COMPUTER, round.getMessage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRoundMessage_IllegalArgumentException() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.SCISSORS);
        Round round = new Round(42, moves, GameConfiguration.COMPUTER);
        round.setMessage(null);
    }


}
