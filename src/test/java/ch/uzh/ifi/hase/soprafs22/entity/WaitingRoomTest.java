package ch.uzh.ifi.hase.soprafs22.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WaitingRoomTest {

    @Test
    public void addPlayerTest(){
        WaitingRoom wr = new WaitingRoom();
        String n1 = "Anna";
        String n2 = "Peter";
        Long id1 = 1L;
        Long id2 = 2L;
        Player p1 = new Player(n1,id1);
        Player p2 = new Player(n2, id2);
        wr.addPlayer(p1);
        assertEquals("Anna", wr.getPlayerNames().get(0));
        wr.addPlayer(p2);
        assertEquals("Peter", wr.getPlayerNames().get(1));
        assertEquals(2L, wr.getPlayerList().get(1).getId());

    }

    @Test
    public void removePlayersTest(){
        //Setup from before:
        WaitingRoom wr = new WaitingRoom();
        String n1 = "Anna";
        String n2 = "Peter";
        Long id1 = 1L;
        Long id2 = 2L;
        Player p1 = new Player(n1,id1);
        Player p2 = new Player(n2, id2);
        wr.addPlayer(p1);
        assertEquals("Anna", wr.getPlayerNames().get(0));
        wr.addPlayer(p2);
        assertEquals("Peter", wr.getPlayerNames().get(1));

        wr.removePlayer("Anna");
        assertFalse(wr.getPlayerNames().contains("Anna"));

        wr.removeAllPlayers();
        assertTrue(wr.getPlayerList().isEmpty());
        wr.removeAllPlayerNames();
        assertTrue(wr.getPlayerNames().isEmpty());

    }
}
