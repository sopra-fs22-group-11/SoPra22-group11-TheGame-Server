package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.List;

public class WaitingRoom {

    private List<Player> playerList = new ArrayList<>();
    private List<String> playerNames = new ArrayList<>();

    public void addPlayer(Player player){
        //if (!checkIfPlayerInList(player)) {
            playerList.add(player);
            playerNames.add(player.getPlayerName());
        //}
    }


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

    public void removeAllPlayerNames() {
        playerNames.clear();
    }

}
