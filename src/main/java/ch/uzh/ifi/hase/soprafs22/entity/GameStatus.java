package ch.uzh.ifi.hase.soprafs22.entity;

public class GameStatus {
    private boolean gameRunning = true; //At creation of the Object the game just started running
    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean userLeft = false; //This is set to true once a single user leaves


    public boolean getGameRunning() {return gameRunning;}




    public boolean getGameWon() {return gameWon;}

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
        this.gameRunning = !this.gameWon && !this.gameLost && !this.userLeft;
    }


    public boolean getGameLost() {return gameLost;}

    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
        this.gameRunning = !this.gameWon && !this.gameLost && !this.userLeft;
    }


    public boolean getUserLeft() {return userLeft;}

    public void setUserLeft(boolean userLeft) {
        this.userLeft = userLeft;
        this.gameRunning = !this.gameWon && !this.gameLost && !this.userLeft;
    }

}
