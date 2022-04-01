package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;

public class Pile {
    private Card topCard;
    private Directions direction;

    public Pile(Directions direction){ this.direction = direction;}



    public boolean checkIfCardCanBePlayed(Card card) throws Exception {
        if (direction == Directions.TOPDOWN){
            if(card.getValue() < topCard.getValue() || card.getValue() + 10  == topCard.getValue())
            {return true;}
            else {return false;}
        }
        if (direction == Directions.DOWNUP){
            if(card.getValue() > topCard.getValue() || card.getValue() - 10  == topCard.getValue())
            {return true;}
            else {return false;}

        }
        throw new Exception(); // TODO the Rest Request which handles playing a card will catch this exception and throw a BadRequestException

    }

    public void setTopCard(Card card){
        this.topCard = card;
    }

    public Card getTopCard(){
        return this.topCard;
    }

}





