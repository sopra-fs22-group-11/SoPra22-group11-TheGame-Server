package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;

import java.util.List;

public class Game {
    private Deck deck = new Deck();
    private List<Pile> pileList;
    private List<User> userList;
    private int fillUpToNoOfCards;


    public void Game(List<User> userList){
        this.userList = userList;

        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.DOWNUP));
        pileList.add(new Pile(Directions.DOWNUP));

        // TODO Add Exact rules for amount of Cards
        fillUpToNoOfCards = 7;
    }

    public User updateCurrentPlayer(User oldUser){
       int oldindex = findPlayerInPlayerList(oldUser);
       int newIndex = (oldindex+1) % userList.size();
       return userList.get(newIndex);
    }

    private int findPlayerInPlayerList(User oldUser){
        int len = userList.size();
        for (int i = 0; i < len; i++){
            if (oldUser.getId().equals(userList.get(i).getId())){
                return i;
            }
        }
        return -1; //TODO throw explicit Exception
    }
    
    public boolean checkWin() {
        // for the moment I made noOfCards public (not so nice)
        // we may change it in Deck to cards.size(), then change it here too
        if (deck.noOfCards == 0) {
            for (User user : userList) {
                if (user.getHandCards().getNoOfCards() != 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // TODO: more a question than to do, but we agreed on not checking lost game, correct? - D: Correct :)

    public void updateWinningCount() { // not updateScore as in diagram
        for (User user : userList) {
            user.setWinningCount(user.getWinningCount()+1);
        }
    }

}
