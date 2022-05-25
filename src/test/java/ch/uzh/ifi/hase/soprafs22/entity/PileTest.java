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




}
