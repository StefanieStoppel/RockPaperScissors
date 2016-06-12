package model;

import com.rockpaperscissors.RockPaperScissorsApplication;
import com.rockpaperscissors.model.Round;
import com.rockpaperscissors.strategy.GameStrategy;
import com.rockpaperscissors.strategy.RockPaperScissorsStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RockPaperScissorsApplication.class)
public class RoundTests {

    private Round round;
    private static final String PLAYER = "Player";
    private static final String COMPUTER = "Computer";
    private final String[] hands = {"rock", "paper", "scissors"};
    private GameStrategy gameStrategy = new RockPaperScissorsStrategy();

    private Map<String, String> moves;
    private String playersChoice;
    private String computersChoice;
    private int winnerIdx;
    private long roundCount;

    @Before
    public void setUp() throws Exception {
        // Random round
        roundCount = ThreadLocalRandom.current().nextLong(0, 10);

        moves = new LinkedHashMap<>();
        playersChoice = getRandomHand();
        moves.put(PLAYER, playersChoice);
        computersChoice = getRandomHand();
        moves.put(COMPUTER, computersChoice);

        int result = gameStrategy.determineWinner(playersChoice, computersChoice);
        winnerIdx = -1;
        if(result == 1) {
            winnerIdx = 0;
        } else if(result == -1) {
            winnerIdx = 1;
        }
        this.round = new Round(roundCount, moves, winnerIdx);
    }

    @Test
    public void testGetters(){
        Assert.assertNotNull(round);

        Assert.assertEquals(round.getCount(), roundCount);
        Assert.assertEquals(round.getMoves(), moves);
        Assert.assertEquals(round.getWinnerId(), winnerIdx);
    }

    private String getRandomHand() {
        int randIdx = ThreadLocalRandom.current().nextInt(0, 3);
        return hands[randIdx];
    }
}
