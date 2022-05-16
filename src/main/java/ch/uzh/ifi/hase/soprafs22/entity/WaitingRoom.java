package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.List;

public class WaitingRoom {
    private final int MaxPlayers = 4;
    private List<Player> playerList = new ArrayList<>();
    private List<String> playerNames = new ArrayList<>();
    private int noOfPlayers;

    public int getNoOfPlayers() {return noOfPlayers;}


    public void addPlayer(Player player){
        //if (!checkIfPlayerInList(player)) {
            playerList.add(player);
            playerNames.add(player.getPlayerName());
        //}
    }

    /*
    public boolean checkIfPlayerInList(Player player) {
        for (int i = 0; i <= playerList.size(); i++) {
            if (this.playerList.get(i).getPlayerName().equals(player.getPlayerName())) {
                return true;
            }
        }
        return false;
    }

     */

    public void removePlayer(String playerName){
        playerNames.remove(playerName);
    }
    public List<Player> getPlayerList (){
        return this.playerList;
    }

    public List<String> getPlayerNames (){
        return this.playerNames;
    }

    public void removeAllPlayers(){
        playerList.clear();
        System.out.println("playerlist size after remove them all: " + playerList.size());
    }


    // only when we have more than two players in waiting room we could start game
    public Boolean checkStartGamePossible() {
        if (noOfPlayers >= 2) {
            return true;
        }
        return false;
    }

}
