package ch.uzh.ifi.hase.soprafs22.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HandCardsTest {
    @Test
    public void getNoCardsTest(){
        HandCards hc = new HandCards();
        assertEquals(0, hc.getNoOfCards());
    }

    @Test
    public void addCardTest(){
        HandCards hc = new HandCards();
        hc.addCard(new Card(12));
        assertEquals(1, hc.getNoOfCards());
        assertEquals(12, hc.getHandCards().get(0).getValue());
    }

    @Test
    public void fillCardsTest(){
        //TODO Implement this test in C.1.2
    }

    @Test
    public void deleteCardTest(){
        HandCards hc = new HandCards();

        Card card1 = new Card(12);
        Card card2 = new Card(14);

        hc.addCard(card1);
        hc.addCard(card2);

        assertEquals(2, hc.getNoOfCards());

        try{ //Success
            hc.deleteCard(card1);
            assertEquals(14, hc.getHandCards().get(0).getValue());
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try{ // Failure
            hc.deleteCard(new Card(13));
        }
        catch (Exception e){
            assertTrue(true); //success
        }
    }


}
