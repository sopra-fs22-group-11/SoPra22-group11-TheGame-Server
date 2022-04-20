package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testUser = new User();
    testUser.setId(1L);
    testUser.setPassword("testName");
    testUser.setUsername("testUsername");

    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
  }

  @Test
  public void createUser_validInputs_success() {
    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    User createdUser = userService.createUser(testUser);

    // then
    Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

    assertEquals(testUser.getId(), createdUser.getId());
    assertEquals(testUser.getPassword(), createdUser.getPassword());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getToken());
    assertEquals(UserStatus.READY, createdUser.getStatus());
  }

  @Test
  public void createUser_duplicateName_throwsException() {
    // given -> a first User has already been created
    userService.createUser(testUser);
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }

  @Test
  public void createUser_duplicateInputs_throwsException() {
    // given -> a first User has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    Mockito.when(userRepository.findByPassword(Mockito.any())).thenReturn(testUser);
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second User with same User -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }


  @Test
  public void login_register_User_changeStatusInRepo() {
      // given -> a first User has already been created
      User createdUser = userService.createUser(testUser);
      userService.setStatusInRepo(createdUser.getId(), UserStatus.READY);
      assertEquals(createdUser.getStatus(), UserStatus.READY);
  }


/*
  @Test
  public void getUserById() {
      // given -> a first User has already been created
      userService.createUser(testUser);

      // when -> setup additional mocks for UserRepository
      Mockito.when(userRepository.findById(testUser.getId()).thenReturn(testUser);

      assertEquals(testUser.getId(), userService.getUserById(testUser.getId()));
  }

 */


  @Test
  public void getUserById_notFound() {
      // given -> a first User has already been created
      userService.createUser(testUser);

      // when -> setup additional mocks for UserRepository
      Mockito.when(userRepository.findById(Mockito.any())).thenReturn(null);

      assertThrows(ResponseStatusException.class, () -> userService.getUserById(testUser.getId()));
  }



  @Test
  public void saveUpdateUser() {
      // given -> a first User has already been created
      User test = userService.createUser(testUser);

      // when -> setup additional mocks for UserRepository
      userRepository.save(test);

  }

 // @Test
 // public void testPlayerCreated() {
 //     userService.createUser(testUser);
 //     Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);
 //     Player test = userService.createPlayer(testUser.getUsername());
 //     assertEquals(test.getUsername(), "testUsername");
//
 // }


}
