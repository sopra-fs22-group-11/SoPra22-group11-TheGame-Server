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
       HandCards hc = new HandCards();
       Deck deck = new Deck();
       assertEquals(98, deck.getNoOfCards());

       hc.fillCards(2, deck); // Two cards on hand
       assertEquals(2, hc.getNoOfCards());
       assertEquals(96, deck.getNoOfCards());

       hc.fillCards(94, deck); // mostly empty the pile
       assertEquals(94, hc.getNoOfCards());
       assertEquals(4, deck.getNoOfCards());

       HandCards hc2 = new HandCards();
       hc2.fillCards(12, deck); // The pile is now mostly empty
       assertEquals(4, hc2.getNoOfCards());
       assertEquals(0, deck.getNoOfCards());
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
