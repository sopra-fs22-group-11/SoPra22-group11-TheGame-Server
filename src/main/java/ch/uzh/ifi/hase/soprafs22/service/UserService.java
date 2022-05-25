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

    newUser = userRepository.save(newUser);
    userRepository.flush();

    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  private void checkIfPlayerExists(User userToBeCreated) {
      User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
      String baseErrorMessage = "The %s provided is not unique. Therefore, the player could not be created!";
      if (userByUsername != null) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username", "is"));
      }
  }



    public void updateScore(Player player, int i){

        // Updates the user with the information given by another User-Object

        Optional<User> foundById = userRepository.findById(player.getId());

        if (foundById.isPresent())
        {   foundById.get().setScore(foundById.get().getScore()+i);
            userRepository.save(foundById.get());
            userRepository.flush();

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
