package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PileTest {
    @Test
    public void getAndSetTopCardTest(){
        Pile pile = new Pile(Directions.DOWNUP);
        pile.setTopCard(new Card(20));

        assertEquals(20, pile.getTopCard().getValue());
    }

    @Test
    public void StartCardTest(){
        Pile pileDU = new Pile(Directions.DOWNUP);
        assertEquals(1, pileDU.getTopCard().getValue());

        Pile pileTD = new Pile(Directions.TOPDOWN);
        assertEquals(100, pileTD.getTopCard().getValue());
    }

    @Test
    public void checkIfCardCanBePlayedDUTest(){
        Pile pileDU = new Pile(Directions.DOWNUP);
        pileDU.setTopCard(new Card(12));

        try{
            assertTrue(pileDU.checkIfCardCanBePlayed(new Card(2))); // Jump
            assertTrue(pileDU.checkIfCardCanBePlayed(new Card(13))); // Strictly bigger
            assertFalse(pileDU.checkIfCardCanBePlayed(new Card(10))); // Strictly smaller no jump
            assertFalse(pileDU.checkIfCardCanBePlayed(new Card(12))); // Same value
        }
        catch (Exception e){
            fail(); // This only happens if the Direction is unknown, which doesn't happen here
        }
    }

    @Test
    public void checkIfCardCanBePlayedTDTest(){
        Pile pileDU = new Pile(Directions.TOPDOWN);
        pileDU.setTopCard(new Card(80));

        try{
            assertTrue(pileDU.checkIfCardCanBePlayed(new Card(90))); // Jump
            assertTrue(pileDU.checkIfCardCanBePlayed(new Card(79))); // Strictly smaller
            assertFalse(pileDU.checkIfCardCanBePlayed(new Card(81))); // Strictly bigger no jump
            assertFalse(pileDU.checkIfCardCanBePlayed(new Card(80))); // Same value
        }
        catch (Exception e){
            fail(); // This only happens if the Direction is unknown, which doesn't happen here
        }
    }



}
