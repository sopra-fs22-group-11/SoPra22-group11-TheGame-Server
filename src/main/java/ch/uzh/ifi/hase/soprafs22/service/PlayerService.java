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
    newPlayer.setStatus(PlayerStatus.READY);

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
   * playername and the name
   * defined in the Player entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param playerToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see Player
   */
  private void checkIfPlayerExists(Player playerToBeCreated) {
      Player playerByPlayername = playerRepository.findByPlayername(playerToBeCreated.getPlayername());
      String baseErrorMessage = "The %s provided is not unique. Therefore, the player could not be created!";
      if (playerByPlayername != null) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "playername", "is"));
      }
  }

    public Player getPlayerById(long playerId) {
        Player player = playerRepository.findById(playerId);
        if (player != null) {
            return player;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This player ID cannot be found.");
    }

    public Player saveUpdate(Player player) {
        player = playerRepository.save(player);
        playerRepository.flush();
        player = getPlayerById(player.getId());
        return player;
    }

    public void setStatusInRepo(long playerId, PlayerStatus playerStatus) {
        List<Player> players = getPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == playerId) {
                players.get(i).setStatus(playerStatus);
                playerRepository.save(players.get(i));
                break;
            }
        }
    }
}
