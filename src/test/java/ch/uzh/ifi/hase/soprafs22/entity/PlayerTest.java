package ch.uzh.ifi.hase.soprafs22.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    public void fillCardsTest(){
        Deck deck = new Deck();
        Player player = new Player("player1", new Long("1"));

        player. fillCards(8,deck);
        assertEquals(8,player.getNoOfCards());
        assertEquals(98-8,deck.getNoOfCards());

    }

}
