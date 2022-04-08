package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.List;

public class WaitingRoom {
    private final int MaxPlayers = 5;
    private List<Player> playerList = new ArrayList<>();
    private int noOfPlayers;

    public int getNoOfPlayers() {return noOfPlayers;}

    public WaitingRoom() {
        for(int i = 2; i<MaxPlayers; i++){
            playerList.add(new Player());
        }
        noOfPlayers = playerList.size();
    }


    // only when we have more than two players in waiting room we could start game
    public Boolean checkStartGamePossible() {
        if (noOfPlayers >= 2) {
            return true;
        }
        return false;
    }

}
