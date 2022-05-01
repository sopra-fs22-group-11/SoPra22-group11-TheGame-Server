package ch.uzh.ifi.hase.soprafs22.entity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final int DeckSize = 100;
    private List<Card> cards = new ArrayList<>();
    //private int noOfCards;

    public int getNoOfCards(){return cards.size();} //cards.size()

    public List<Card> removeCardOnDeck(){
        cards.remove(0);
        return cards;
    }


    public Deck(){ // Constructor of the Deck
        for(int i = 2; i<DeckSize; i++){
            cards.add(new Card(i));
        }
        shuffle();
        //noOfCards = cards.size();
    }

    private void shuffle(){Collections.shuffle(cards);}

    public Card pop(){ // Make sure to only allow pop for noOfCards > 0
        Card cardToReturn  = cards.get(0);
        cards.remove(0);
        //noOfCards -= 1;
        return cardToReturn;
        }

}
