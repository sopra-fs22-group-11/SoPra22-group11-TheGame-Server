package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    //TODO tests not ok yet
    @Test
    public void updateCurrentPlayerTest(){
        // TODO Test this in C.2.1 Whose turn

/*
       // Player player1 = new Player();
       // Player player2 = new Player();
//
       // //Another functionality would take care of this - namely playerService
       // player1.setGameCount(0);
       // player2.setGameCount(0);
//
       // List<Player> pl = new ArrayList<>();
       // pl.add(player1);
       // pl.add(player2);
//
       //Game game = Game.initializeGame(pl, userService);

        User user1 = new User();
        User user2 = new User();

        //Another functionality would take care of this - namely playerService
        user1.setGameCount(0);
        user2.setGameCount(0);

        List<User> pl = new ArrayList<>();
        pl.add(user1);
        pl.add(user2);

        //Game game = Game.initializeGame(pl, userService);
*/
    }

    @Test
    public void Game_StatusCheck() {
        List<Pile> pileList = new ArrayList<>();
        int fillUpToNoOfCards;

        testUser = new User();
        testUser.setId(1L);
        testUser.setPassword("testName");
        testUser.setUsername("testUsername");
        testUser.setStatus(UserStatus.READY);

        List<User> userList = new ArrayList<>();
        userList.add(testUser);
        userList.add(testUser);

        // initially status of each user is ready
        for (User user:userList) {
            assertEquals(user.getStatus(), UserStatus.READY);
        }
/*
        Player testPlayer = new Player(testUser.getUsername(), testUser.getId());
        Game game = new Game();
        game.addPlayer(testPlayer);
        game.addPlayer(testPlayer);
        game.startGame();

        //after game started status of each user changes to ingame
        for (User user:userList) {
            assertEquals(user.getStatus(), UserStatus.INGAME);
        }

 */

    }
}
