package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Card;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.Status;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class DTOMapperTest {
  @Test
  public void testCreatePlayer_fromPlayerPostDTO_toPlayer_success() {
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
  public void testGetPlayer_fromPlayer_toPlayerGetDTO_success() {
    // create Player
    Player player = new Player();
    player.setUsername("Firstname Lastname");
    player.setPassword("pw");
    player.setStatus(PlayerStatus.OFFLINE);
    player.setToken("1");
    player.setWinningCount(0);
    player.setGameCount(0);
    player.setYourTurn(true);

    // MAP -> Create PlayerGetDTO
    PlayerGetDTO playerGetDTO = DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(player);

    // check content
    assertEquals(player.getId(), playerGetDTO.getId());
    assertEquals(player.getPassword(), playerGetDTO.getPassword());
    assertEquals(player.getUsername(), playerGetDTO.getUsername());
    assertEquals(player.getStatus(), playerGetDTO.getStatus());
    assertEquals(player.getWinningCount(), playerGetDTO.getWinningCount());
    assertEquals(player.getGameCount(), playerGetDTO.getGameCount());
    assertEquals(player.getYourTurn(), playerGetDTO.getYourTurn());
  }

    @Test
    public void testCreatePlayer_fromPlayerPutDTO_toPlayer_success() {
        // create PlayerPostDTO
        PlayerPutDTO playerPutDTO = new PlayerPutDTO();
        playerPutDTO.setUsername("Firstname Lastname");
        playerPutDTO.setPassword("pw");
        playerPutDTO.setStatus(PlayerStatus.OFFLINE);
        playerPutDTO.setWinningCount(0);
        playerPutDTO.setGameCount(0);

        Player player = DTOMapper.INSTANCE.convertPlayerPutDTOtoEntity(playerPutDTO);

        // check content
        assertEquals(playerPutDTO.getPassword(), player.getPassword());
        assertEquals(playerPutDTO.getUsername(), player.getUsername());
        assertEquals(playerPutDTO.getStatus(), player.getStatus());
        assertEquals(playerPutDTO.getWinningCount(), player.getWinningCount());
        assertEquals(playerPutDTO.getGameCount(), player.getGameCount());
        assertEquals(player.getId(), playerPutDTO.getId());
    }

    @Test
    public void testGetGame_fromGame_toGameGetDTO_success() {
        //TODO test GameDTO
    }

    @Test
    public void testGetStatus_fromStatus_toStatusGetDTO_success() {
        // create Status
        Status status = new Status();
        Player p = new Player();
        status.setPlayerTurn(p);

        // MAP -> Create StatusGetDTO
        StatusGetDTO statusGetDTO = DTOMapper.INSTANCE.convertEntityToStatusGetDTO(status);

        // check content
        assertEquals(status.getGameRunning(), statusGetDTO.getGameRunning());
        assertEquals(status.getGameWon(), statusGetDTO.getGameWon());
        assertEquals(status.getGameLost(), statusGetDTO.getGameLost());
        assertEquals(status.getPlayerLeft(), statusGetDTO.getPlayerLeft());
        assertEquals(status.getPlayerTurn(), statusGetDTO.getPlayerTurn());

    }

    @Test
    public void testGetCard_fromCard_toCardGetDTO_success() {
        // create Card
        Card card = new Card(12);

        // MAP -> Create CardGetDTO
        CardGetDTO cardGetDTO = DTOMapper.INSTANCE.convertEntityToCardGetDTO(card);

        // check content
        assertEquals(card.getValue(), cardGetDTO.getValue());


    }


}
