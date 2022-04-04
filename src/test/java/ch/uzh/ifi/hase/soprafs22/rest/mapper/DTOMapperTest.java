package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Card;
import ch.uzh.ifi.hase.soprafs22.entity.Status;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
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
    // create UserPostDTO
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("username");
    userPostDTO.setPassword("password");

    // MAP -> Create user
    User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // check content
    assertEquals(userPostDTO.getPassword(), user.getPassword());
    assertEquals(userPostDTO.getUsername(), user.getUsername());
  }

  @Test
  public void testGetUser_fromUser_toUserGetDTO_success() {
    // create User
    User user = new User();
    user.setUsername("Firstname Lastname");
    user.setPassword("pw");
    user.setStatus(UserStatus.OFFLINE);
    user.setToken("1");
    user.setWinningCount(0);
    user.setGameCount(0);
    user.setYourTurn(true);

    // MAP -> Create UserGetDTO
    UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

    // check content
    assertEquals(user.getId(), userGetDTO.getId());
    assertEquals(user.getPassword(), userGetDTO.getPassword());
    assertEquals(user.getUsername(), userGetDTO.getUsername());
    assertEquals(user.getStatus(), userGetDTO.getStatus());
    assertEquals(user.getWinningCount(), userGetDTO.getWinningCount());
    assertEquals(user.getGameCount(), userGetDTO.getGameCount());
    assertEquals(user.getYourTurn(), userGetDTO.getYourTurn());
  }


    @Test
    public void testCreateUser_fromUserPutDTO_toUser_success() {
        // create UserPostDTO
        UserPutDTO userPutDTO = new UserPutDTO();
          userPutDTO.setUsername("Firstname Lastname");
          userPutDTO.setPassword("pw");
          userPutDTO.setStatus(UserStatus.OFFLINE);
          userPutDTO.setWinningCount(0);
          userPutDTO.setGameCount(0);

        User user = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);

        // check content
        assertEquals(userPutDTO.getPassword(), user.getPassword());
        assertEquals(userPutDTO.getUsername(), user.getUsername());
        assertEquals(userPutDTO.getStatus(), user.getStatus());
        assertEquals(userPutDTO.getWinningCount(), user.getWinningCount());
        assertEquals(userPutDTO.getGameCount(), user.getGameCount());
        assertEquals(user.getId(), userPutDTO.getId());
    }

    @Test
    public void testGetGame_fromGame_toGameGetDTO_success() {
        //TODO test GameDTO
    }

    @Test
    public void testGetStatus_fromStatus_toStatusGetDTO_success() {
        // create Status
        Status status = new Status();
        User user = new User();
        status.setUserTurn(user);

        // MAP -> Create StatusGetDTO
        StatusGetDTO statusGetDTO = DTOMapper.INSTANCE.convertEntityToStatusGetDTO(status);

        // check content
        assertEquals(status.getGameRunning(), statusGetDTO.getGameRunning());
        assertEquals(status.getGameWon(), statusGetDTO.getGameWon());
        assertEquals(status.getGameLost(), statusGetDTO.getGameLost());
        assertEquals(status.getUserLeft(), statusGetDTO.getUserLeft());
        assertEquals(status.getUserTurn(), statusGetDTO.getUserTurn());

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
