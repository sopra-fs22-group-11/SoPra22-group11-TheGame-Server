package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Deck;
import ch.uzh.ifi.hase.soprafs22.entity.User;

import java.util.ArrayList;
import java.util.List;

public class GameGetDTO<Piles> {
    //TODO discuss with group
    //private Long gameId; // We at the moment don't have a gameId to be passed around, since we only have one Game
    private List<User> userList = new ArrayList<>();
    private Deck deck;
    private List<Piles> pilesList = new ArrayList<>();


    public List<User> getUserList() {return userList;}

    public void setUserList(List<User> playerList) {
        this.userList = userList;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public List<Piles> getPilesList() {
        return pilesList;
    }

    public void setPilesList(List<Piles> pilesList) {
        this.pilesList = pilesList;
    }




}
