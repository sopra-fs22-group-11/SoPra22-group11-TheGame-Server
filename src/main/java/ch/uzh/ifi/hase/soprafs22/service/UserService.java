package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
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
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<Player> getUsers() {
    return this.userRepository.findAll();
  }

  public Player createUser(Player newPlayer) {
    newPlayer.setToken(UUID.randomUUID().toString());
    newPlayer.setStatus(PlayerStatus.OFFLINE);

    checkIfUserExists(newPlayer);

    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newPlayer = userRepository.save(newPlayer);
    userRepository.flush();

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
  private void checkIfUserExists(Player playerToBeCreated) {
    Player playerByUsername = userRepository.findByUsername(playerToBeCreated.getUsername());
    Player playerByName = userRepository.findByName(playerToBeCreated.getPassword());

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
}
