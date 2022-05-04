package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.*;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

  DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

  @Mapping(source = "password", target = "password")
  @Mapping(source = "username", target = "username")
  User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "score", target = "score")
  //@Mapping(source = "gameCount", target = "gameCount")
  UserGetDTO convertEntityToUserGetDTO(User user);


  @Mapping(source = "id", target = "id")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "score", target = "score")
  //@Mapping(source = "gameCount", target = "gameCount")
  User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);



  // TODO delete this soon
  /*@Mapping(source = "userList", target = "userList")
  @Mapping(source = "deck", target = "deck")
  @Mapping(source = "pilesList", target = "pilesList")
  GameGetDTO convertEntityToGameGetDTO(Game game);
*/

  //@Mapping(source = "gameRunning", target = "gameRunning")
  //@Mapping(source = "gameWon", target = "gameWon")
  //@Mapping(source = "gameLost", target = "gameLost")
  //@Mapping(source = "userLeft", target = "userLeft")
  //@Mapping(source = "userTurn", target = "userTurn")
  //GameStatusGetDTO convertEntityToStatusGetDTO(GameStatus gameStatus);

/*
  @Mapping(source = "value", target = "value")
  CardGetDTO convertEntityToCardGetDTO(Card card);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "playerName", target = "playerName")
  @Mapping(source = "handCards", target = "handCards")
  PlayerGetDTO convertEntityToPlayerGetDTO(Player player);
*/
}
