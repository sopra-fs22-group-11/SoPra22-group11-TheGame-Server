package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class PlayerRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private PlayerRepository playerRepository;

  @Test
  public void findByPassword_success() {
    // given
    Player player = new Player();
    player.setPlayername("Firstname Lastname");
    player.setPassword("firstname@lastname");
    player.setStatus(PlayerStatus.OFFLINE);
    player.setToken("1");

    entityManager.persist(player);
    entityManager.flush();

    // when
    Player found = playerRepository.findByPassword(player.getPassword());

    // then
    assertNotNull(found.getId());
    assertEquals(found.getPassword(), player.getPassword());
    assertEquals(found.getPlayername(), player.getPlayername());
    assertEquals(found.getToken(), player.getToken());
    assertEquals(found.getStatus(), player.getStatus());
  }
}
