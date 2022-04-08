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
        Player player1 = new Player();
        Player player2 = new Player();

        //Another functionality would take care of this - namely playerService
        player1.setGameCount(0);
        player2.setGameCount(0);

        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);

       Game game = Game.initializeGame(pl, userService);

    }
}
