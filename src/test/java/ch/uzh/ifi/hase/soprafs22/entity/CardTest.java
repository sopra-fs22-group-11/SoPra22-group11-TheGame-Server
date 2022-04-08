package ch.uzh.ifi.hase.soprafs22.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    @Test
    public void getCardTest(){
        Card card = new Card(12);
        assertEquals(12, card.getValue());
    }
}
