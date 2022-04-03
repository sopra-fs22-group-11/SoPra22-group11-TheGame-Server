package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PlayerControllerTest
 * This is a WebMvcTest which allows to test the PlayerController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the PlayerController works.
 */
@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PlayerService playerService;

  @Test
  public void givenPlayers_whenGetPlayers_thenReturnJsonArray() throws Exception {
    // given
    Player player = new Player();
    player.setPlayername("Firstname Lastname");
    player.setPassword("firstname@lastname");
    player.setStatus(PlayerStatus.OFFLINE);

    List<Player> allPlayers = Collections.singletonList(player);

    // this mocks the PlayerService -> we define above what the playerService should
    // return when getPlayers() is called
    given(playerService.getPlayers()).willReturn(allPlayers);

    // when
    MockHttpServletRequestBuilder getRequest = get("/players").contentType(MediaType.APPLICATION_JSON);

    // then
    mockMvc.perform(getRequest).andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].password", is(player.getPassword())))
        .andExpect(jsonPath("$[0].playername", is(player.getPlayername())))
        .andExpect(jsonPath("$[0].status", is(player.getStatus().toString())));
  }

  @Test
  public void createPlayer_validInput_playerCreated() throws Exception {
    // given
    Player player = new Player();
    player.setId(1L);
    player.setPlayername("Test Player");
    player.setPassword("testplayername");
    player.setToken("1");
    player.setStatus(PlayerStatus.READY);

    PlayerPostDTO playerPostDTO = new PlayerPostDTO();
    playerPostDTO.setPassword("Test Player");
    playerPostDTO.setPlayername("testplayername");

    given(playerService.createPlayer(Mockito.any())).willReturn(player);

    // when/then -> do the request + validate the result
    MockHttpServletRequestBuilder postRequest = post("/players")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(playerPostDTO));

    // then
    mockMvc.perform(postRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(player.getId().intValue())))
        .andExpect(jsonPath("$.password", is(player.getPassword())))
        .andExpect(jsonPath("$.playername", is(player.getPlayername())))
        .andExpect(jsonPath("$.status", is(player.getStatus().toString())));
  }

    @Test
    public void createPlayer_invalidInput_playerNotCreated() throws Exception {

        given(playerService.createPlayer(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.CONFLICT,
                String.format("The playername provided is not unique. Therefore, the player could not be created!")));

        PlayerPostDTO playerPostDTO = new PlayerPostDTO();
        playerPostDTO.setPassword("Test Player");
        playerPostDTO.setPlayername("testplayername");


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(playerPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isConflict());
    }

    @Test
    public void loginPlayer_validInput_playerLogggedIn() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setPassword("Test Player");
        player.setPlayername("testplayername");
        player.setToken("1");
        player.setStatus(PlayerStatus.OFFLINE);

        PlayerPostDTO playerPostDTO = new PlayerPostDTO();
        playerPostDTO.setPassword("Test Player");
        playerPostDTO.setPlayername("testplayername");

        List<Player> allPlayers = Collections.singletonList(player);

        given(playerService.getPlayers()).willReturn(allPlayers);
        //given(playerService.setStatusInRepo(player.getId(player.getId())));

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(playerPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(player.getId().intValue())))
                .andExpect(jsonPath("$.password", is(player.getPassword())))
                .andExpect(jsonPath("$.playername", is(player.getPlayername())))
                .andExpect(jsonPath("$.status", is(player.getStatus().toString())));
    }

    @Test
    public void loginPlayer_invalidPassword() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setPlayername("Test Player");
        player.setPassword("testplayername");
        player.setToken("1");
        player.setStatus(PlayerStatus.OFFLINE);

        PlayerPostDTO playerPostDTO = new PlayerPostDTO();
        playerPostDTO.setPlayername("Test Player");
        playerPostDTO.setPassword("wrongPassword");

        List<Player> allPlayers = Collections.singletonList(player);

        given(playerService.getPlayers()).willReturn(allPlayers);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(playerPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginPlayer_notRegistered() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setPlayername("Test Player");
        player.setPassword("testplayername");
        player.setToken("1");
        player.setStatus(PlayerStatus.OFFLINE);

        PlayerPostDTO playerPostDTO = new PlayerPostDTO();
        playerPostDTO.setPlayername("TestNotRegistered");
        playerPostDTO.setPassword("testplayername");

        List<Player> allPlayers = Collections.singletonList(player);

        given(playerService.getPlayers()).willReturn(allPlayers);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(playerPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePlayer_success() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setPassword("Test Player");
        player.setPlayername("testplayername");
        player.setToken("1");
        player.setStatus(PlayerStatus.READY);

        PlayerPostDTO playerPostDTO = new PlayerPostDTO();
        playerPostDTO.setPassword("Test Player");
        playerPostDTO.setPlayername("testplayername");

        given(playerService.getPlayerById(player.getId())).willReturn(player);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/players/{playerId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(playerPostDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    public void updatePlayer_IdNotFound_fail() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setPassword("Test Player");
        player.setPlayername("testplayername");
        player.setToken("1");
        player.setStatus(PlayerStatus.READY);

        PlayerPostDTO playerPostDTO = new PlayerPostDTO();
        playerPostDTO.setPassword("Test Player");
        playerPostDTO.setPlayername("testplayername");

        given(playerService.getPlayerById(player.getId())).willReturn(null);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/players/{playerId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(playerPostDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNotFound());
    }



  /**
   * Helper Method to convert userPostDTO into a JSON string such that the input
   * can be processed
   * Input will look like this: {"name": "Test Player", "username": "testUsername"}
   * 
   * @param object
   * @return string
   */
  private String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format("The request body could not be created.%s", e.toString()));
    }
  }
}