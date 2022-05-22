package ch.uzh.ifi.hase.soprafs22.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStatusTest {

    @Test
    public void gameLostTest(){
        GameStatus gs = new GameStatus();
        gs.setGameLost(true);
        assertEquals(true, gs.getGameLost());
        assertFalse(gs.getGameWon());
        assertFalse(gs.getGameRunning());
    }

    @Test
    public void userLeftTest(){
        GameStatus gs = new GameStatus();
        gs.setUserLeft(true);
        assertTrue(gs.getUserLeft());
        assertFalse(gs.getGameRunning());
    }

}
