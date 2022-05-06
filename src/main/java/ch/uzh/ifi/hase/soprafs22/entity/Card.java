package ch.uzh.ifi.hase.soprafs22.entity;

public class Card implements Comparable<Card> {
    private int value;

    @Override
    public int compareTo(Card anotherCard) {
        if (this.value < anotherCard.value) {
            return -1;
        }
        if (this.value > anotherCard.value) {
            return 1;
        }
        return 0;
    }

    public Card(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

}
