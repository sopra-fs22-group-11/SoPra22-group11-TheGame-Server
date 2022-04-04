package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.User;

public class StatusGetDTO {
    private boolean gameRunning;
    private boolean gameWon;
    private boolean gameLost;
    private boolean userLeft;
    private User userTurn;

    public boolean getGameRunning() {return gameRunning;}

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }


    public boolean getGameWon() {return gameWon;}

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }


    public boolean getGameLost() {return gameLost;}

    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }


    public boolean getUserLeft() {return userLeft;}

    public void setUserLeft(boolean userLeft) {
        this.userLeft = userLeft;
    }


    public User getUserTurn() {return userTurn;}

    public void setUserTurn(User userTurn) {
        this.userTurn = userTurn;
    }
}


