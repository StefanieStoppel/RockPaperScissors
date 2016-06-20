package com.rockpaperscissors.service;

import com.rockpaperscissors.RockPaperScissorsApplication;
import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.helper.HandFactory;
import com.rockpaperscissors.model.OutputTemplate;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.strategy.RockPaperScissorsStrategy;
import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Stefanie Stoppel
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RockPaperScissorsApplication.class)
public class GameServiceTest {

    @Autowired
    GameService gameService;
    private Map<String, String> moves;

    @Before
    public void setUp() throws Exception {
        // Play game mode rock, paper, scissors
        gameService.setStrategyByGameMode(GameConfiguration.GAME_MODE_RPS);
    }

    public void setUpMoves(String playershand, String computershand) {
        moves = new LinkedHashMap<>();
        moves.put(GameConfiguration.PLAYER, playershand);
        moves.put(GameConfiguration.COMPUTER, computershand);
    }

    @Test
    public void testDraw() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.PAPER);

        Round resultRound = gameService.playRound(GameConfiguration.PAPER, GameConfiguration.PAPER);

        Assert.assertNotNull(resultRound);

        Assert.assertEquals(moves, resultRound.getMoves());
        Assert.assertEquals(Strings.EMPTY, resultRound.getWinner());
    }

    @Test
    public void testPlayerWins() {
        setUpMoves(GameConfiguration.SCISSORS, GameConfiguration.PAPER);

        Round round = gameService.playRound(GameConfiguration.SCISSORS, GameConfiguration.PAPER);

        Assert.assertNotNull(round);
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals(GameConfiguration.PLAYER, round.getWinner());
    }

    @Test
    public void testComputerWins() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.SCISSORS);

        Round round = gameService.playRound(GameConfiguration.PAPER, GameConfiguration.SCISSORS);

        Assert.assertNotNull(round);
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals(GameConfiguration.COMPUTER, round.getWinner());
    }

    @Test
    public void testInvalidHands() {
        // 1) Player's hand valid, computer's hand invalid
        String invalidHand = HandFactory.getRandomInvalidHand();
        setUpMoves(GameConfiguration.ROCK, invalidHand);
        Round round = gameService.playRound(GameConfiguration.ROCK, invalidHand);

        Assert.assertNotNull(round);
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals(Strings.EMPTY, round.getWinner());
        Assert.assertEquals(OutputTemplate.ERROR_INVALID_HAND_COMPUTER, round.getMessage());

        // 2) Player's hand invalid, computer's hand valid
        invalidHand = HandFactory.getRandomInvalidHand();
        setUpMoves(invalidHand, GameConfiguration.SCISSORS);
        round = gameService.playRound(invalidHand, GameConfiguration.SCISSORS);

        Assert.assertNotNull(round);
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals(Strings.EMPTY, round.getWinner());
        Assert.assertEquals(OutputTemplate.ERROR_INVALID_HAND_PLAYER, round.getMessage());

        // 3) Player's hand invalid, computer's hand invalid
        invalidHand = HandFactory.getRandomInvalidHand();
        String invalidhand2 = HandFactory.getRandomInvalidHand();
        setUpMoves(invalidHand, invalidhand2);
        round = gameService.playRound(invalidHand, invalidhand2);

        Assert.assertNotNull(round);
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals(Strings.EMPTY, round.getWinner());
        Assert.assertEquals(OutputTemplate.ERROR_INVALID_HAND_PLAYER, round.getMessage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidhandPlayer_IllegalArgumentException() {
        // 1) Player's hand null, computer's hand valid
        setUpMoves(null, GameConfiguration.ROCK);
        Round round = gameService.playRound(null, GameConfiguration.ROCK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidhandComputer_IllegalArgumentException() {
        // 1) Player's hand null, computer's hand valid
        setUpMoves(GameConfiguration.ROCK, null);
        Round round = gameService.playRound(GameConfiguration.ROCK, null);
    }

    @Test
    public void testSetStrategyByGameMode() {
        gameService.setStrategyByGameMode(0);
        Assert.assertEquals(RockPaperScissorsStrategy.class, gameService.getGameStrategy().getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStrategyByGameMode_IllegalArgumentException() {
        gameService.setStrategyByGameMode(-1);
    }
}