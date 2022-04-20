package ch.uzh.ifi.hase.soprafs22.entity;

public class GameStatus {
    private boolean gameRunning = true; //At creation of the Object the game just started running
    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean userLeft = false; //I assume this is set to true once a single user leaves
    private String userTurn = "Anna";  // TODO Delete userTurn and check that in GameTransfer Correct
    // Todo userTurn redundant in Game, GameStatus and User - choose one !

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


    public String getUserTurn() {return userTurn;}


}
