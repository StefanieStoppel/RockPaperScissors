package model;

import com.rockpaperscissors.RockPaperScissorsApplication;
import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.service.GameService;
import com.rockpaperscissors.strategy.GameStrategy;
import com.rockpaperscissors.strategy.RockPaperScissorsStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ThreadLocalRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RockPaperScissorsApplication.class)
public class RoundTests {

    @Autowired
    GameService gameService;

    private static final String[] invalidChoices = {"jfshf", "üfj(", "$(§$KE", "", "-4", "..", "papr", "0034358325"};

    private Round round;

    private String playersChoice;
    private String computersChoice;

    @Before
    public void setUp() throws Exception {
        // Random round
        gameService.setGameModeAndStrategy(GameConfiguration.GAME_MODE_RPS);
    }

    public void setUpValidChoices() {
        setUpChoices(true, true);
    }

    public void setUpChoices(boolean playersChoiceValid, boolean computersChoiceValid) {
        playersChoice = playersChoiceValid ? gameService.getRandomChoice() : getRandomInvalidChoice();
        computersChoice = computersChoiceValid ? gameService.getRandomChoice() : getRandomInvalidChoice();
    }

    public String getRandomInvalidChoice() {
        return invalidChoices[ThreadLocalRandom.current().nextInt(0, invalidChoices.length)];
    }

    @Test
    public void testValidChoices() {
        setUpValidChoices();
        gameService.play(playersChoice, computersChoice);
        round = gameService.getRound();

        Assert.assertNotNull(round);

        String winner = gameService.getWinner(playersChoice, computersChoice);
        Assert.assertEquals(winner, round.getWinner());
    }

    @Test
    public void testInvalidChoices() {

        // 1) Player's choice valid, computer's choice invalid
        setUpChoices(true, false);
        gameService.play(playersChoice, computersChoice);
        round = gameService.getRound();

        Assert.assertNotNull(round);
        Assert.assertNull(round.getWinner());

        // 2) Player's choice invalid, computer's choice valid
        setUpChoices(false, true);
        gameService.play(playersChoice, computersChoice);
        round = gameService.getRound();

        Assert.assertNotNull(round);
        Assert.assertNull(round.getWinner());

        // 3) Player's choice invalid, computer's choice invalid
        setUpChoices(false, false);
        gameService.play(playersChoice, computersChoice);
        round = gameService.getRound();

        Assert.assertNotNull(round);
        Assert.assertNull(round.getWinner());
    }

    @Test
    public void testRoundWinner() {
        setUpValidChoices();
        gameService.play(playersChoice, computersChoice);
        round = gameService.getRound();
        String winner = gameService.getWinner(gameService.getPlayersChoice(), gameService.getComputersChoice());
        Assert.assertEquals(winner, round.getWinner());
    }

    @Test
    public void testRoundCount() {
        setUpValidChoices();
        gameService.play(playersChoice, computersChoice);
        round = gameService.getRound();
        long roundCount = gameService.getRoundCount();
        Assert.assertEquals(roundCount, round.getCount());
    }

}
