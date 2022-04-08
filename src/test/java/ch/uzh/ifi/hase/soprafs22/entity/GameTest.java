package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    public void updateCurrentPlayerTest(){
        // TODO Test this in C.2.1 Whose turn
        User user1 = new User();
        User user2 = new User();

        //Another functionality would take care of this - namely playerService
        user1.setGameCount(0);
        user2.setGameCount(0);

        List<User> pl = new ArrayList<>();
        pl.add(user1);
        pl.add(user2);

       Game game = Game.initializeGame(pl, userService);

    }
}
