package com.rockpaperscissors.model;

import com.rockpaperscissors.RockPaperScissorsApplication;
import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.helper.HandFactory;
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
        gameService.setGameModeAndStrategy(GameConfiguration.GAME_MODE_RPS);
    }

    public void setUpMoves(String playersChoice, String computersChoice) {
        moves = new LinkedHashMap<>();
        moves.put(GameConfiguration.PLAYER, playersChoice);
        moves.put(GameConfiguration.COMPUTER, computersChoice);
    }

    @Test
    public void testDraw() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.PAPER);

        Round r = new Round(roundCount, moves, "");
        Round resultRound = gameService.playRound(GameConfiguration.PAPER, GameConfiguration.PAPER);

        Assert.assertNotNull(resultRound);

        Assert.assertEquals(r.getMoves(), resultRound.getMoves());
        Assert.assertEquals(r.getWinner(), resultRound.getWinner());
    }

    @Test
    public void testPlayerWins() {
        setUpMoves(GameConfiguration.SCISSORS, GameConfiguration.PAPER);

        Round r = new Round(roundCount, moves, GameConfiguration.PLAYER);
        Round round = gameService.playRound(GameConfiguration.SCISSORS, GameConfiguration.PAPER);

        Assert.assertNotNull(round);
        Assert.assertEquals(r.getMoves(), round.getMoves());
        Assert.assertEquals(r.getWinner(), round.getWinner());
    }

    @Test
    public void testComputerWins() {
        setUpMoves(GameConfiguration.PAPER, GameConfiguration.SCISSORS);

        Round r = new Round(roundCount, moves, GameConfiguration.COMPUTER);
        Round round = gameService.playRound(GameConfiguration.PAPER, GameConfiguration.SCISSORS);

        Assert.assertNotNull(round);
        Assert.assertEquals(r.getMoves(), round.getMoves());
        Assert.assertEquals(r.getWinner(), round.getWinner());
    }

    @Test
    public void testInvalidChoices() {
        roundCount = roundCounter.longValue();

        // 1) Player's choice valid, computer's choice invalid
        String invalidChoice = HandFactory.getRandomInvalidHand();
        setUpMoves(GameConfiguration.ROCK, invalidChoice);
        Round round = gameService.playRound(GameConfiguration.ROCK, invalidChoice);

        Assert.assertNotNull(round);
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals(OutputTemplate.ERROR_INVALID_CHOICE_COMPUTER, round.getWinner());

        // 2) Player's choice invalid, computer's choice valid
        invalidChoice = HandFactory.getRandomInvalidHand();
        setUpMoves(invalidChoice, GameConfiguration.SCISSORS);
        round = gameService.playRound(invalidChoice, GameConfiguration.SCISSORS);

        Assert.assertNotNull(round);
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals(OutputTemplate.ERROR_INVALID_CHOICE_PLAYER, round.getWinner());

        // 3) Player's choice invalid, computer's choice invalid
        invalidChoice = HandFactory.getRandomInvalidHand();
        String invalidChoice2 = HandFactory.getRandomInvalidHand();
        setUpMoves(invalidChoice, invalidChoice2);
        round = gameService.playRound(invalidChoice, invalidChoice2);

        Assert.assertNotNull(round);
        Assert.assertEquals(moves, round.getMoves());
        Assert.assertEquals(OutputTemplate.ERROR_INVALID_CHOICE_PLAYER, round.getWinner());
    }
}
