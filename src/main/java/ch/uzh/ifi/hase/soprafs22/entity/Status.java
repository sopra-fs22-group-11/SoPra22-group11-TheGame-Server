package ch.uzh.ifi.hase.soprafs22.entity;

public class Status {
    private boolean gameRunning = true; //At creation of the Object the game just started running
    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean playerLeft = false; //I assume this is set to true once a single player leaves
    private Player playerTurn; // If no one is using it here we can remove it
    // Todo playerTurn redundant in Game, Status and Player - choose one !

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
