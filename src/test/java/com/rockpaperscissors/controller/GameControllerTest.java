package com.rockpaperscissors.controller;

import com.rockpaperscissors.RockPaperScissorsApplication;
import com.rockpaperscissors.config.GameConfiguration;
import com.rockpaperscissors.helper.HandFactory;
import com.rockpaperscissors.model.OutputTemplate;
import com.rockpaperscissors.service.GameService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.lang.Math.toIntExact;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Stefanie Stoppel
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RockPaperScissorsApplication.class)
public class GameControllerTest {

    private static Logger logger = Logger.getLogger(GameControllerTest.class);

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private GameService gameService;

    @Autowired
    GameConfiguration gameConfiguration;

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void playRockPaperScissors_validHand() throws Exception {
        gameService.setStrategyByGameMode(GameConfiguration.GAME_MODE_RPS);
        gameConfiguration.setGameMode(GameConfiguration.GAME_MODE_RPS);

        checkValidHand();
    }

    @Test
    public void playRockPaperScissors_invalidHand() throws Exception {
        gameService.setStrategyByGameMode(GameConfiguration.GAME_MODE_RPS);
        gameConfiguration.setGameMode(GameConfiguration.GAME_MODE_RPS);

        checkInvalidHand();
    }

    @Test
    public void playRockPaperScissorsWell_validHand() throws Exception {
        // Set game mode to 1 = "Rock Paper Scissors Well"
        gameService.setStrategyByGameMode(GameConfiguration.GAME_MODE_RPSW);
        gameConfiguration.setGameMode(GameConfiguration.GAME_MODE_RPSW);

        checkValidHand();
    }

    @Test
    public void playRockPaperScissorsWell_invalidHand() throws Exception {
        // Set game mode to 1 = "Rock Paper Scissors Well"
        gameService.setStrategyByGameMode(GameConfiguration.GAME_MODE_RPSW);
        gameConfiguration.setGameMode(GameConfiguration.GAME_MODE_RPSW);

        checkInvalidHand();
    }

    private void checkValidHand() throws Exception {
        String randomValidHand = HandFactory.getRandomValidHand(GameConfiguration.GAME_MODE);
        logger.debug("ValidChoice: "+ randomValidHand);

        mockMvc.perform(get("/play/" + GameConfiguration.GAME_MODE + "?hand=" + randomValidHand))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.count").value(toIntExact(gameService.getRoundCount())))
                .andExpect(jsonPath("$.moves").isNotEmpty())
                .andExpect(jsonPath("$.winner").value(anyOf(is(""), is(GameConfiguration.COMPUTER), is(GameConfiguration.PLAYER))))
                .andExpect(jsonPath("$.message").isEmpty());
    }

    private void checkInvalidHand() throws Exception {
        String randomInvalidHand = HandFactory.getRandomInvalidHand();
        logger.debug("InvalidChoice: "+ randomInvalidHand);

        mockMvc.perform(get("/play/" + GameConfiguration.GAME_MODE + "?hand=" + randomInvalidHand))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.count").value(-1))
                .andExpect(jsonPath("$.moves").isEmpty())
                .andExpect(jsonPath("$.winner").value(""))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value(OutputTemplate.ERROR_INVALID_CHOICE_PLAYER));
    }


}