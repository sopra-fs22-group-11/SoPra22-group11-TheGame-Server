package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private String playerName;
    private Long id; // same as user id
    private List<Card> handCards = new ArrayList<Card>();

    public Player(String playerName, Long id){
        this.playerName = playerName;
        this.id = id;
    }


    public List<Card> getHandCards(){return handCards;}
    public void setHandCards(List<Card> handCards){this.handCards = handCards;}

    public String getPlayerName(){return this.playerName;}

    public Long getId(){return this.id;}

    public int getNoOfCards(){
        return handCards.size();
    }

    public Deck fillCards(int fillTo, Deck deck){
        while(getNoOfCards() < fillTo && deck.getNoOfCards() > 0){
            handCards.add(deck.pop());
            //deck.removeCardOnDeck();
        }
        System.out.println("deck size in fillCards"+deck.getNoOfCards());
        return deck;
    }

}

