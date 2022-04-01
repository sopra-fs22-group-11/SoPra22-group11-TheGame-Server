package ch.uzh.ifi.hase.soprafs22.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HandCards implements Serializable {
    private List<Card> handCards = new ArrayList<>();
    private int noOfCards = 0;

    public int getNoOfCards(){
        return noOfCards;
    }

    public void fillCards(int fillTo){
        //TODO Implement this function in C.1.2
    }

    public void addCard(Card card) {
        handCards.add(card);
        noOfCards += 1;
    }

    public void deleteCard(Card cardToBeDeleted) throws Exception { // TODO the Rest request which handles playing a card must also catch this Exception and throw a BadRequestException
        int index = findCardByValue(cardToBeDeleted.getValue());
        handCards.remove(index);
        noOfCards -= 1;

    }

    public List<Card> getHandCards(){
        return handCards;
    }


    //TODO: Feel free to implement more elegant way of deleting a card if you'd like

    private  int findCardByValue(int val) throws Exception {
        for (int i = 0; i< noOfCards; i++ ) {
            if (handCards.get(i).getValue() == val) {
                return i;
            }
        }
        throw new Exception();

    }
}

