package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class DTOMapperTest {
  @Test
  public void testCreateUser_fromUserPostDTO_toUser_success() {
    // create PlayerPostDTO
    PlayerPostDTO playerPostDTO = new PlayerPostDTO();
    playerPostDTO.setUsername("username");
    playerPostDTO.setPassword("password");

    // MAP -> Create player
    Player player = DTOMapper.INSTANCE.convertPlayerPostDTOtoEntity(playerPostDTO);

    // check content
    assertEquals(playerPostDTO.getPassword(), player.getPassword());
    assertEquals(playerPostDTO.getUsername(), player.getUsername());
  }

  @Test
  public void testGetUser_fromUser_toUserGetDTO_success() {
    // create Player
    Player player = new Player();
    player.setUsername("Firstname Lastname");
    player.setPassword("firstname@lastname");
    player.setStatus(PlayerStatus.OFFLINE);
    player.setToken("1");

    // MAP -> Create PlayerGetDTO
    PlayerGetDTO playerGetDTO = DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(player);

    // check content
    assertEquals(player.getId(), playerGetDTO.getId());
    assertEquals(player.getPassword(), playerGetDTO.getPassword());
    assertEquals(player.getUsername(), playerGetDTO.getUsername());
    assertEquals(player.getStatus(), playerGetDTO.getStatus());
  }
}
