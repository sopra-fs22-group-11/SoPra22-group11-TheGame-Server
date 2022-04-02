package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Player Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class PlayerService {

  private final Logger log = LoggerFactory.getLogger(PlayerService.class);

  private final PlayerRepository playerRepository;

  @Autowired
  public PlayerService(@Qualifier("playerRepository") PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public List<Player> getPlayers() {
    return this.playerRepository.findAll();
  }

  public Player createPlayer(Player newPlayer) {
    newPlayer.setToken(UUID.randomUUID().toString());
    newPlayer.setStatus(PlayerStatus.OFFLINE);
    newPlayer.setGameCount(0);
    newPlayer.setWinningCount(0);

    checkIfPlayerExists(newPlayer);

    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newPlayer = playerRepository.save(newPlayer);
    playerRepository.flush();

    log.debug("Created Information for Player: {}", newPlayer);
    return newPlayer;
  }

  /**
   * This is a helper method that will check the uniqueness criteria of the
   * username and the name
   * defined in the Player entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param playerToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see Player
   */
  private void checkIfPlayerExists(Player playerToBeCreated) {
    Player playerByUsername = playerRepository.findByUsername(playerToBeCreated.getUsername());
    Player playerByName = playerRepository.findByPassword(playerToBeCreated.getPassword());

    String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
    if (playerByUsername != null && playerByName != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format(baseErrorMessage, "username and the name", "are"));
    } else if (playerByUsername != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username", "is"));
    } else if (playerByName != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "name", "is"));
    }
  }

  public void increaseGameCount(Player player, int i){
      // Updates the user with the information given by another User-Object

      Optional<Player> foundById = playerRepository.findById(player.getId());

      foundById.get().setGameCount(player.getGameCount()+i);

      playerRepository.save(foundById.get());
      playerRepository.flush();
  }

    public void savePlayer(Player updatedPlayer){

        Optional<Player> foundById = playerRepository.findById(updatedPlayer.getId());

        foundById.get().setYourTurn(updatedPlayer.getYourTurn());
        foundById.get().setWinningCount(updatedPlayer.getWinningCount());
        foundById.get().setGameCount(updatedPlayer.getGameCount());
        foundById.get().setHandCards(updatedPlayer.getHandCards());

        playerRepository.save(foundById.get());
        playerRepository.flush();
    }

    public void increaseWinningCount(Player player, int i){
        // TODO Make sure this works and test it in C.3.3 Winning the Game
        // Updates the user with the information given by another User-Object

        Optional<Player> foundById = playerRepository.findById(player.getId());

        foundById.get().setWinningCount(player.getWinningCount()+i);

        playerRepository.save(foundById.get());
        playerRepository.flush();
    }
}
