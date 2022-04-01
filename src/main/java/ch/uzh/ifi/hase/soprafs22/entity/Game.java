package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;
import org.springframework.data.domain.Sort;

import java.util.List;

public class Game{
    private Deck deck = new Deck();
    private List<Pile> pileList;
    private List<Player> playerList;
    private int fillUpToNoOfCards;


    public void Game(List<Player> playerList) throws Exception {
        // Store all players
        this.playerList = playerList;

        // Add all piles
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.DOWNUP));
        pileList.add(new Pile(Directions.DOWNUP));

        // Check what's the amount of cards on a hand
        if(playerList.size()==2) { fillUpToNoOfCards = 7; }
        else if (playerList.size() <= 5 && playerList.size() >= 3){ fillUpToNoOfCards = 6;}
        else{throw new Exception();}// TODO The REST request which handles the game start will catch this exception and throw a ResponseStatusException

        // Fill all users handcards
        //TODO actually do that then

        // Every player now has a game more they played
        for(Player player:playerList){
            player.setGameCount(player.getGameCount() + 1);
        }

    }

    public Player updateCurrentPlayer(Player oldPlayer){
       int oldIndex = findPlayerInPlayerList(oldPlayer);
       int newIndex = (oldIndex+1) % playerList.size();
       return playerList.get(newIndex);
    }

    private int findPlayerInPlayerList(Player oldPlayer){
        int len = playerList.size();
        for (int i = 0; i < len; i++){
            if (oldPlayer.getId().equals(playerList.get(i).getId())){
                return i;
            }
        }
        return -1; //TODO throw explicit Exception
    }
    
    public boolean checkWin() {
        if (deck.getNoOfCards() == 0) {
            for (Player player : playerList) {
                if (player.getHandCards().getNoOfCards() != 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    public void updateWinningCount() {
        for (Player player : playerList) {
            player.setWinningCount(player.getWinningCount()+1);
        }
    }

}
