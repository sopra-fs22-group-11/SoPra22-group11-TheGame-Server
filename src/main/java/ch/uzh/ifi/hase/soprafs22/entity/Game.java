package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;
import org.springframework.data.domain.Sort;

import java.util.List;

public class Game {
    private Deck deck = new Deck();
    private List<Pile> pileList;
    private List<Player> playerList;
    private int fillUpToNoOfCards;


    public void Game(List<Player> playerList){
        this.playerList = playerList;

        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.DOWNUP));
        pileList.add(new Pile(Directions.DOWNUP));

        // TODO Add Exact rules for amount of Cards
        fillUpToNoOfCards = 7;
    }

    public Player updateCurrentPlayer(Player oldPlayer){
       int oldindex = findPlayerInPlayerList(oldPlayer);
       int newIndex = (oldindex+1) % playerList.size();
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
        // for the moment I made noOfCards public (not so nice)
        // we may change it in Deck to cards.size(), then change it here too
        if (deck.noOfCards == 0) {
            for (Player player : playerList) {
                if (player.getHandCards().getNoOfCards() != 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // TODO: more a question than to do, but we agreed on not checking lost game, correct? - D: Correct :)

    public void updateWinningCount() { // not updateScore as in diagram
        for (Player player : playerList) {
            player.setWinningCount(player.getWinningCount()+1);
        }
    }

}
