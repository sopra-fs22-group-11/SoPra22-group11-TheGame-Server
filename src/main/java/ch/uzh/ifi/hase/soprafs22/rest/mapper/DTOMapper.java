package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.*;
import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the Player) to the external/API representation (e.g.,
 * PlayerGetDTO for getting, PlayerPostDTO for creating)
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
  Player convertPlayerPostDTOtoEntity(PlayerPostDTO playerPostDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "yourTurn", target = "yourTurn")
  @Mapping(source = "winningCount", target = "winningCount")
  @Mapping(source = "gameCount", target = "gameCount")
  PlayerGetDTO convertEntityToPlayerGetDTO(Player player);


  @Mapping(source = "id", target = "id")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "winningCount", target = "winningCount")
  @Mapping(source = "gameCount", target = "gameCount")
  Player convertPlayerPutDTOtoEntity(PlayerPutDTO playerPutDTO);


  /*@Mapping(source = "playerList", target = "playerList")
  @Mapping(source = "deck", target = "deck")
  @Mapping(source = "pilesList", target = "pilesList")*/
  GameGetDTO convertEntityToGameGetDTO(Game game);


  @Mapping(source = "gameRunning", target = "gameRunning")
  @Mapping(source = "gameWon", target = "gameWon")
  @Mapping(source = "gameLost", target = "gameLost")
  @Mapping(source = "playerLeft", target = "playerLeft")
  @Mapping(source = "playerTurn", target = "playerTurn")
  StatusGetDTO convertEntityToStatusGetDTO(Status status);


  @Mapping(source = "value", target = "value")
  CardGetDTO convertEntityToCardGetDTO(Card card);
}
