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


    public void setTopCard(Card card){
        this.topCard = card;
    }

    public Card getTopCard(){
        return this.topCard;
    }

}





