package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;
import ch.uzh.ifi.hase.soprafs22.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class Game{
    private Deck deck = new Deck();
    private List<Pile> pileList = new ArrayList<>();
    private List<Player> playerList;
    private int fillUpToNoOfCards;
    private UserService userService;
    private GameStatus gameStatus = new GameStatus();

    public static Game initializeGame(List<Player> playerList, UserService pc){
        // TODO This is how we create a new player unless there is another option how to pass playerList

        // Store all players
        Game game = new Game();
        game.playerList = playerList;

        // TODO give the singular PlayerService Element to the Game such that it can make changes to the database
        game.userService = pc;
        return game;
    }

    private void Game() throws Exception { // This is private to remember to create a player as above
        // Add all piles
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.DOWNUP));
        pileList.add(new Pile(Directions.DOWNUP));

        // Check what's the amount of cards on a hand
        if(playerList.size()==2) { fillUpToNoOfCards = 7; }
        else if (playerList.size() <= 5 && playerList.size() >= 3){ fillUpToNoOfCards = 6;}
        else{throw new Exception();}// TODO The REST request which handles the game start will catch this exception and throw a ResponseStatusException

        for(Player player:playerList){
            // Fill all users hand-cards
            player.getHandCards().fillCards(fillUpToNoOfCards);

            // Every player now has a game more they played
            userService.increaseGameCount(player, 1);
        }

    }

    public User updateCurrentPlayer(User oldUser) throws Exception { // TODO the Rest Request which will handle the "end of turn" will have to catch this exception and throw a BadRequestException
       // TODO Make sure this works in C.2.1 Whose turn
        int oldIndex = findUserInUserList(oldUser);
       int newIndex = (oldIndex+1) % playerList.size();
       // TODO also change player.yourTurn and gameStatus.playerTurn
       return playerList.get(newIndex);
    }

    //TODO Feel free to implement a more elegant solution if you want
    private int findUserInUserList(User oldUser) throws Exception {
        int len = playerList.size();
        for (int i = 0; i < len; i++){
            if (oldUser.getId().equals(playerList.get(i).getId())){
                return i;
            }
        }
        throw new Exception();
    }
    
    public boolean checkWin() {
        // TODO Make sure this works and test it in C.3.3 Winning the Game
        if (deck.getNoOfCards() == 0) {
            for (Player player : playerList) {
                if (player.getHandCards().getNoOfCards() != 0) {
                    return false;
                }
            }
            gameStatus.setGameWon(true);
            return true;
        }
        return false;
    }


    public void updateWinningCount() {
        // TODO Make sure this works and test it in C.3.3 Winning the Game
        for (Player player : playerList) {
            userService.increaseWinningCount(player, 1);
        }
    }

}
