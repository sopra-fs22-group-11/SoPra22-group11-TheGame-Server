package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class UserServiceIntegrationTest {

  @Qualifier("userRepository")
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @BeforeEach
  public void setup() {
    userRepository.deleteAll();
  }

  @Test
  public void createUser_validInputs_success() {
    // given
    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setPassword("testName");
    testUser.setUsername("testUsername");

    // when
    User createdUser = userService.createUser(testUser);

    // then
    assertEquals(testUser.getId(), createdUser.getId());
    assertEquals(testUser.getPassword(), createdUser.getPassword());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getToken());
    assertEquals(UserStatus.READY, createdUser.getStatus());
  }

  @Test
  public void createUser_duplicateUsername_throwsException() {
    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setPassword("testName");
    testUser.setUsername("testUsername");
    User createdUser = userService.createUser(testUser);

    // attempt to create second user with same Username
    User testUser2 = new User();

    // change the name but forget about the Username
    testUser2.setPassword("testName2");
    testUser2.setUsername("testUsername");

    // check that an error is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser2));
  }

    @Test
    public void update_User_Success() {
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setPassword("testName");
        testUser.setUsername("testUsername");
        User createdUser = userService.createUser(testUser);

        // create user to update old user with
        User updatedUser = new User();
        long id = createdUser.getId();

        updatedUser.setPassword("testPassword2");
        updatedUser.setUsername("testUsername2");
        updatedUser.setId(id);

        userService.updateUser(updatedUser, id);

        assertEquals(id,createdUser.getId());
        assertEquals("testPassword2" ,userService.getUserById(id).getPassword());
        assertEquals( "testUsername2",userService.getUserById(id).getUsername());
    }

    @Test
    public void update_Score(){ //tests that score of new user is 0 and wheter update score works
        User testUser = new User();
        testUser.setPassword("testName");
        testUser.setUsername("testUsername");
        User createdUser = userService.createUser(testUser);
        long id = createdUser.getId();
        Player newPlayer = new Player(createdUser.getUsername(), createdUser.getId());
        assertEquals( 0,userService.getUserById(id).getScore());

        userService.updateScore(newPlayer,200);
        assertEquals( 200,userService.getUserById(id).getScore());


    }

    @Test
    public void duplicate_Username(){
        User testUser = new User();
        testUser.setPassword("testName");
        testUser.setUsername("testUsername");
        User createdUser = userService.createUser(testUser);
        long id = createdUser.getId();
        long id2 = id+1;


        assertEquals( true,userService.checkForDuplicateUsername("testUsername",id2));


    }




}
