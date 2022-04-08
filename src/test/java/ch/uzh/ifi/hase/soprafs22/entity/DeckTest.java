package ch.uzh.ifi.hase.soprafs22.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {

    @Test
    public void DeckTest(){

        Deck deck = new Deck();
        assertEquals(98, deck.getNoOfCards()); // Tests constructor and getNoOfCards
        // We are (currently) not testing the shuffle method

        Card poppedCard = deck.pop();
        assertEquals(97, deck.getNoOfCards());

    }
}
