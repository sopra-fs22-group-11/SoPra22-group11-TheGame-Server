package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;

public class Pile {
    private Card topCard;
    private Directions direction;

    public Pile(Directions direction){
        this.direction = direction;
        if (direction == Directions.TOPDOWN){
            this.topCard = new Card(100); // If we start at the top the topmost card implicitly is 100
        }
        else {
            this.topCard = new Card(1); // If we start from the bottom the topnost cart implicitly is 1
        }
    }


    // TODO this will be in the client
    public boolean checkIfCardCanBePlayed(Card card) throws Exception {
        if (direction == Directions.TOPDOWN){
            if(card.getValue() < topCard.getValue() || card.getValue() - 10  == topCard.getValue())
            {return true;}
            else {return false;}
        }
        if (direction == Directions.DOWNUP){
            if(card.getValue() > topCard.getValue() || card.getValue() + 10  == topCard.getValue())
            {return true;}
            else {return false;}

        }
        throw new Exception(); // This exception should actually never be necessary
        // TODO the Rest Request which handles playing a card will catch this exception and throw a BadRequestException

    }

    public void setTopCard(Card card){
        this.topCard = card;
    }

    public Card getTopCard(){
        return this.topCard;
    }

}





