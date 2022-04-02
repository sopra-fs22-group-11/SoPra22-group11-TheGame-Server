package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Player;

public class StatusGetDTO {
    private boolean gameRunning;
    private boolean gameWon;
    private boolean gameLost;
    private boolean playerLeft;
    private Player playerTurn;

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


    public boolean getPlayerLeft() {return playerLeft;}

    public void setPlayerLeft(boolean playerLeft) {
        this.playerLeft = playerLeft;
    }


    public Player getPlayerTurn() {return playerTurn;}

    public void setPlayerTurn(Player playerTurn) {
        this.playerTurn = playerTurn;
    }
}


