package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class Game{
    // we need some sort of gameId for each game: private final int gameId;
    private Deck deck = new Deck();
    private List<Pile> pileList = new ArrayList<>();
    private GameStatus gameStatus = new GameStatus();

    private List<Player> playerList;
    private String whoseTurn; //playerName
    private int fillUpToNoOfCards;

    private UserService userService;


    public void Game(UserService userService){
        // Generates the pile
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.DOWNUP));
        pileList.add(new Pile(Directions.DOWNUP));
    }


    public void startGame(List<Player> playerList, UserService userService) {
        //TODO call this in the app/start endpoint of websocket
        this.playerList = playerList;
        this.userService = userService;


        // Check what's the amount of cards on a hand
        if(playerList.size()==2) { fillUpToNoOfCards = 7; }
        else if (playerList.size() <= 5 && playerList.size() >= 3){ fillUpToNoOfCards = 6;}
        //else{throw new Exception();}// TODO The Websocket request which handles the game start will catch this exception and throw a ResponseStatusException


        for(Player player:playerList){
            // Fill all users hand-cards
            player.fillCards(fillUpToNoOfCards, deck);

            // Every player now has a game more they played
            //TODO do this
            //userService.increaseGameCount(player, 1);

            //TODO change the status of each player
             /*
            // Every player is now in game, change its status
            player.setStatus(UserStatus.INGAME);
            */
            }
        whoseTurn = playerList.get(0).getPlayerName();





        // TODO: delete this

        /*while (gamePlayable()) {
            // game can be played -- continue
        }
        // end the game
        if (checkWin()) {
            onGameOver();
        }
        onGameOver();
    */
    }
    /*
    public boolean gamePlayable() {
        if (deck.getNoOfCards() == 0) {
            for (Player player:playerList) {
                if (player.getHandCards().getNoOfCards() == 0) {
                    return false;
                } return true; }
        }
        return true;
    }
    */

    public void playCard() {}


    public void updateCurrentPlayer() {
        //TODO Call this in app/draw, make sure the app/draw can only be called after the app/discard
        String newPlayer = onePlayerFurther(whoseTurn);
        Player playerObject = playerList.get(findPlayerInPlayerList(newPlayer));

        //Failsafe counter
        int cnt = 0;
        while (playerObject.getNoOfCards()<1 & cnt <= playerList.size()){
            newPlayer = onePlayerFurther(newPlayer);
            playerObject = playerList.get(findPlayerInPlayerList(newPlayer));
            cnt += 1;
        }

        whoseTurn = newPlayer;
    }
    public String onePlayerFurther(String oldPlayer){  // TODO throws Exception { : the Rest Request which will handle the "end of turn" will have to catch this exception and throw a BadRequestException
        int oldIndex = findPlayerInPlayerList(oldPlayer);
        int newIndex = (oldIndex+1) % playerList.size();

        return playerList.get(newIndex).getPlayerName();
    }

    //TODO Feel free to implement a more elegant solution if you want
    private int findPlayerInPlayerList(String oldPlayer) {// TODO throws Exception {
        int len = playerList.size();
        for (int i = 0; i < len; i++){
            if (oldPlayer.equals(playerList.get(i).getPlayerName())){
                return i;
            }
        }
        return -200;
        //TODO throw new Exception();
    }
    
    public boolean checkWin() {
        // TODO Make sure this works and test it in C.3.3 Winning the Game
        if (deck.getNoOfCards() == 0) {
            for (Player player : playerList) {
                if (player.getNoOfCards() != 0) {
                    return false;
                }
            }
            gameStatus.setGameWon(true);
            /*for (User user : userList) {
                user.setStatus(UserStatus.READY);
            }*/
            return true;
        }
        return false;
    }

    public boolean gameOver() {
        // checks on a button click when a player says he cannot play anymore
        if (true) { return true;}
        return false;
    }

    //private void onGameWon() {
    //    for (Player player:playerList) {
    //        userService.increaseWinningCount(player, 1);
    //    }
    //    // TODO: end game in gameService? could end the game with given gameId
    //    // TODO: delete players also in GameService
    //}

    private void onGameOver() {
        /*
        for (Player player:playerList) {
            userService.increaseGameCount(player, 1);
        }
         */
        // TODO: end game in gameService? could end the game with given gameId
        // TODO: delete players also in GameService
    }


    /*
    public void updateWinningCount() {
        // TODO Make sure this works and test it in C.3.3 Winning the Game
        for (Player player : playerList) {
            userService.increaseWinningCount(player, 1);
        }
    }

     */

    public List<Player> getListOfPlayers() {
        return this.playerList;
    }

    public List<Pile> getPileList(){return  this.pileList;
    }
    public GameStatus getGameStatus(){return this.gameStatus;}

    public void addPlayer(Player player) {this.getListOfPlayers().add(player);}

    public String getWhoseTurn(){
        return this.whoseTurn;
    }

}
