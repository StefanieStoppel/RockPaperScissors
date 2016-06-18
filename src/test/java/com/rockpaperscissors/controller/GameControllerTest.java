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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.anyOf;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void playRockPaperScissors_validHand() throws Exception {
        gameService.setGameModeAndStrategy(GameConfiguration.GAME_MODE_RPS);
        String randomValidHand = HandFactory.getRandomValidHand(GameConfiguration.GAME_MODE_RPS);
        logger.debug("RandomChoice: "+ randomValidHand);

        checkValidHand(randomValidHand);
    }

    @Test
    public void playRockPaperScissors_invalidHand() throws Exception {
        gameService.setGameModeAndStrategy(GameConfiguration.GAME_MODE_RPS);
        String randomInvalidChoice = HandFactory.getRandomInvalidHand();
        logger.debug("InvalidChoice: " + randomInvalidChoice);

        checkInvalidHand(randomInvalidChoice);
    }

    @Test
    public void playRockPaperScissorsWell_validHand()  {
        // Set game mode to 0 = "Rock Paper Scissors"
        gameService.setGameModeAndStrategy(GameConfiguration.GAME_MODE_RPSW);
        String randomValidHand = HandFactory.getRandomValidHand(GameConfiguration.GAME_MODE_RPSW);
        logger.debug("ValidChoice: "+ randomValidHand);

        checkValidHand(randomValidHand);
    }

    @Test
    public void playRockPaperScissorsWell_invalidHand()  {
        // Set game mode to 0 = "Rock Paper Scissors"
        gameService.setGameModeAndStrategy(GameConfiguration.GAME_MODE_RPSW);
        String randomInvalidHand = HandFactory.getRandomInvalidHand();
        logger.debug("InvalidChoice: "+ randomInvalidHand);

        checkInvalidHand(randomInvalidHand);
    }

    private void checkValidHand(String playersHand) {
        try {
            String responseJSON = getJsonResponse(playersHand);

            // getRoundCount() call after playing a round. The roundCount is now 1.
            long roundCount = gameService.getRoundCount();

            assertThat(responseJSON, anyOf(containsString(String.format(OutputTemplate.WINNER, GameConfiguration.PLAYER, roundCount)),
                    containsString(String.format(OutputTemplate.WINNER, GameConfiguration.COMPUTER, roundCount)),
                    containsString(String.format(OutputTemplate.DRAW, roundCount))));
            assertThat(responseJSON, containsString("You played: " + playersHand));
        } catch (Exception e) {
            logger.error("getJsonResponse(" + playersHand + ") call returned an error: " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void checkInvalidHand(String playersHand) {
        try {
            String responseJSON = getJsonResponse(playersHand);
            assertThat(responseJSON, containsString(OutputTemplate.ERROR_INVALID_CHOICE_PLAYER));
        } catch (Exception e) {
            logger.error("getJsonResponse(" + playersHand + ") call returned an error: " + Arrays.toString(e.getStackTrace()));
        }
    }

    private String getJsonResponse(String playersHand) throws Exception {
        MvcResult result = mockMvc.perform(get("/play/0?hand=" + playersHand))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        return response.getContentAsString();
    }
}