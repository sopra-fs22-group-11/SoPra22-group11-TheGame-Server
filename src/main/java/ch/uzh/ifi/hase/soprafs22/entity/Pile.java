package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;

public class Pile {
    private Card topCard;
    private Directions direction;

    public Pile(Directions direction){ this.direction = direction;}



    public boolean checkIfCardCanBePlayed(Card card){
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
        return false; //TODO  Maybe throw an exception

    }

    public void setTopCard(Card card){
        this.topCard = card;
    }

    public Card getTopCard(){
        return this.topCard;
    }

}





