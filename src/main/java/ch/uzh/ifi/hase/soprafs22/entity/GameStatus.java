package ch.uzh.ifi.hase.soprafs22.entity;

public class GameStatus {
    private boolean gameRunning = true; //At creation of the Object the game just started running
    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean userLeft = false; //I assume this is set to true once a single user leaves
    private User userTurn; // If no one is using it here we can remove it
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


    public User getUserTurn() {return userTurn;}

    public void setUserTurn(User userTurn) {
        this.userTurn = userTurn;
    }
}
