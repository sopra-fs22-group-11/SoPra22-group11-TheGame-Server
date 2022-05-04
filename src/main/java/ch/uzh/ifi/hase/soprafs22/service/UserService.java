package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import com.solidfire.gson.Gson;
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
 * User Service
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

  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

  public User createUser(User newUser) {
    newUser.setToken(UUID.randomUUID().toString());
    newUser.setStatus(UserStatus.READY);
    newUser.setScore(0);


    checkIfPlayerExists(newUser);

    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();

    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }


  /**
   * This is a helper method that will check the uniqueness criteria of the
   * playername and the name
   * defined in the User entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param userToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see User
   */
  private void checkIfPlayerExists(User userToBeCreated) {
      User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
      String baseErrorMessage = "The %s provided is not unique. Therefore, the player could not be created!";
      if (userByUsername != null) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username", "is"));
      }
  }

  //lic void increaseGameCount(Player player, int i){
  // // Updates the user with the information given by another User-Object
  // Optional<User> foundById = userRepository.findById(player.getId());

  // if (foundById.isPresent())
  // {   foundById.get().setGameCount(foundById.get().getGameCount()+i);
  //     userRepository.save(foundById.get());
  //     userRepository.flush();
  // }
  // else{
  //     //TODO Throw Exception
  // }

  //


    public void updateScore(Player player, int i){
        // TODO Make sure this works and test it in C.3.3 Winning the Game
        // Updates the user with the information given by another User-Object

        Optional<User> foundById = userRepository.findById(player.getId());

        if (foundById.isPresent())
        {   foundById.get().setScore(foundById.get().getScore()+i);
            userRepository.save(foundById.get());
            userRepository.flush();

        }
        else{
            //TODO Throw Exception
        }

    }

    public User getUserById(long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            return user;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user ID cannot be found.");
    }

    public User getUserByUsername(String userName){
        User user = userRepository.findByUsername(userName);
        if (user != null) {
            return user;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user cannot be found.");
    }

    public User saveUpdate(User user) {
        user = userRepository.save(user);
        userRepository.flush();
        user = getUserById(user.getId());
        return user;
    }
    public boolean checkForDuplicateUsername(String username, long id){
        // Used in put, returns true if there is another user with the same
        // username which does NOT have the same Id and therefore is not that user
        User userSameUsername = userRepository.findByUsername(username);
        if(userSameUsername != null){
            if (!(userSameUsername.getId() == (id))){
                return true;
            }
        }
        return false;
    }


    public void updateUser(User updatedUser, long id){
        User userDB = getUserById(id);

        /*if (userDB ==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The user with userId %s was not found.", userId));
        }*/ //Should never even be necessary and otherwise UserController takes care of it

        if (updatedUser.getUsername() != null) {
            userDB.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getUsername() != null) {
            userDB.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getPassword() != null) {
            userDB.setPassword(updatedUser.getPassword());
        }
        System.out.println(new Gson().toJson(userDB));

        userRepository.save(userDB);
        userRepository.flush();
    }

    public void setStatusInRepo(long playerId, UserStatus userStatus) {
        List<User> users = getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == playerId) {
                users.get(i).setStatus(userStatus);
                userRepository.save(users.get(i));
                break;
            }
        }
    }
}
