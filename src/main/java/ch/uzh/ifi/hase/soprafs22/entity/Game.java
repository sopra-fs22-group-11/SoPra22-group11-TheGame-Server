package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class Game{
    private Deck deck = new Deck();
    private List<Pile> pileList = new ArrayList<>();
    private List<Player> playerList;
    private int fillUpToNoOfCards;
    private PlayerService playerService;
    private Status status = new Status();

    public static Game initializeGame(List<Player> playerList, PlayerService ps) {
        // TODO This is how we create a new player unless there is another option how to pass playerList

        // Store all players
        Game game = new Game();
        game.playerList = playerList;

        game.playerService = ps;

        System.out.println("Hello");
        System.out.println(game.playerList.size());


        System.out.println(game.playerList.get(0).getHandCards());

        for(Player player:game.playerList){
            // Fill all users hand-cards
            player.getHandCards().fillCards(game.fillUpToNoOfCards, game.deck);

            // Every player now has a game more they played
            player.setGameCount(player.getGameCount()+1);

            player.setHandCards(new HandCards());

            player.setYourTurn(false);

            game.playerService.savePlayer(player);
        }

        // Set Start player
        game.playerList.get(0).setYourTurn(true);
        game.playerService.savePlayer(game.playerList.get(0));
        game.status.setPlayerTurn(game.playerList.get(0));


        // Check what's the amount of cards on a hand
        if(game.playerList.size()==2) { game.fillUpToNoOfCards = 7; }
        else if (game.playerList.size() <= 5 && game.playerList.size() >= 3){ game.fillUpToNoOfCards = 6;}
        else{}//throw new Exception();}// TODO The REST request which handles the game start will catch this exception and throw a ResponseStatusException


        return game;
    }

    private void Game()  { // This is private to remember to create a player as above
        // Add all piles
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.DOWNUP));
        pileList.add(new Pile(Directions.DOWNUP));

    }
    public boolean endOfTurnPossible() {
        int cardNo = status.getPlayerTurn().getHandCards().getNoOfCards();
        if (deck.getNoOfCards() == 0 && cardNo <= fillUpToNoOfCards -1){
            return true;
        }
        else if (deck.getNoOfCards() > 0 && cardNo <= fillUpToNoOfCards -2){
            return true;
        }
        else {return false;}

    }


    public void updateCurrentPlayerAndFill() throws Exception {
       Player oldPlayer = status.getPlayerTurn();
       int oldIndex = findPlayerInPlayerList(oldPlayer);
       int newIndex = (oldIndex+1) % playerList.size();

       status.setPlayerTurn(playerList.get(newIndex));
       oldPlayer.setYourTurn(false);
       playerService.savePlayer(oldPlayer);

       playerList.get(newIndex).setYourTurn(true);
       playerService.savePlayer(playerList.get(newIndex));

       oldPlayer.getHandCards().fillCards(fillUpToNoOfCards, deck);
    }
    //TODO Feel free to implement a more elegant solution if you want
    private int findPlayerInPlayerList(Player oldPlayer) throws Exception {
        int len = playerList.size();
        for (int i = 0; i < len; i++){
            if (oldPlayer.getId().equals(playerList.get(i).getId())){
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
            status.setGameWon(true);
            return true;
        }
        return false;
    }


    public void updateWinningCount() {
        // TODO Make sure this works and test it in C.3.3 Winning the Game
        for (Player player : playerList) {
            player.setWinningCount(player.getWinningCount() + 1);
            playerService.savePlayer(player);

        }
    }


}
