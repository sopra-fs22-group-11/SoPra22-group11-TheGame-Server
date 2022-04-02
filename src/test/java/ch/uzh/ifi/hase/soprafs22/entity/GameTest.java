package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;


    @Test
    public void updateCurrentPlayerAndFillTest(){
        // TODO Test this in C.2.1 Whose turn
        Player player1 = new Player();
        Player player2 = new Player();

        //Another functionality would take care of this - namely playerService
        player1.setGameCount(0);
        player2.setGameCount(0);

        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);

       //Game game = Game.initializeGame(pl, playerService);

    }

    @Test
    public void ExceptionWhenSinglePlayer(){

        Player player1 = new Player();
        player1.setGameCount(0);
        List<Player> pl = new ArrayList<>();
        pl.add(player1);

        try {
            Game game = Game.initializeGame(pl, playerService);
        }
        catch (Exception e){
           assertTrue(true);
        }

    }

    @Test
    public void endOfTurnPossibleTest(){
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setGameCount(0);
        player2.setGameCount(0);
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);

        // TODO



    }
}
