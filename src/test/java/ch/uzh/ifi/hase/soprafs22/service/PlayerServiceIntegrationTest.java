package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see PlayerService
 */
@WebAppConfiguration
@SpringBootTest
public class PlayerServiceIntegrationTest {

  @Qualifier("playerRepository")
  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private PlayerService playerService;

  @BeforeEach
  public void setup() {
    playerRepository.deleteAll();
  }

  @Test
  public void createPlayer_validInputs_success() {
    // given
    assertNull(playerRepository.findByUsername("testUsername"));

    Player testPlayer = new Player();
    testPlayer.setPassword("testName");
    testPlayer.setUsername("testUsername");

    // when
    Player createdPlayer = playerService.createPlayer(testPlayer);

    // then
    assertEquals(testPlayer.getId(), createdPlayer.getId());
    assertEquals(testPlayer.getPassword(), createdPlayer.getPassword());
    assertEquals(testPlayer.getUsername(), createdPlayer.getUsername());
    assertNotNull(createdPlayer.getToken());
    assertEquals(PlayerStatus.OFFLINE, createdPlayer.getStatus());
  }

  @Test
  public void createPlayer_duplicateUsername_throwsException() {
    assertNull(playerRepository.findByUsername("testUsername"));

    Player testPlayer = new Player();
    testPlayer.setPassword("testName");
    testPlayer.setUsername("testUsername");
    Player createdPlayer = playerService.createPlayer(testPlayer);

    // attempt to create second user with same username
    Player testPlayer2 = new Player();

    // change the name but forget about the username
    testPlayer2.setPassword("testName2");
    testPlayer2.setUsername("testUsername");

    // check that an error is thrown
    assertThrows(ResponseStatusException.class, () -> playerService.createPlayer(testPlayer2));
  }
}