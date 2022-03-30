package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.List;

public class Game {
    private Deck deck = new Deck();
    private List<Pile> pileList;
    private List<Player> playerList;

    public Player updateCurrentPlayer(Player player){
        Player currentPlayer = new Player();
        // TODO: implement logic to decide whose player turn it is

        return currentPlayer;
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
