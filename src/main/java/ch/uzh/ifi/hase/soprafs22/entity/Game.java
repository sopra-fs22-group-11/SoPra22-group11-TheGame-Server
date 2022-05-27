package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class Game{
    private Deck deck;
    private List<Pile> pileList = new ArrayList<>();
    private GameStatus gameStatus = new GameStatus();

    private List<Player> playerList;
    private String whoseTurn; //a playerName
    private int fillUpToNoOfCards;

    private UserService userService;



    public void startGame(List<Player> playerList, UserService userService) {
        this.deck = new Deck();
        this.playerList = playerList;
        this.userService = userService;

        //generate piles
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.DOWNUP));
        pileList.add(new Pile(Directions.DOWNUP));
        // Check what's the amount of cards on a hand
        if(playerList.size()==2) { fillUpToNoOfCards = 7; }
        else if (playerList.size() <= 4 && playerList.size() >= 3){ fillUpToNoOfCards = 6;}

        for(Player player:playerList){
            // Fill all users hand-cards
            this.deck = player.fillCards(fillUpToNoOfCards, this.deck);

            //Change the status of each player
            userService.setStatusInRepo(player.getId(), UserStatus.INGAME);
            }
        if(!playerList.isEmpty()) {
            whoseTurn = playerList.get(0).getPlayerName();
        }
        else{
            whoseTurn = "No players in playerlist";
        }


    }



    public void updateGameFromTGOInformation(TransferGameObject tgo){
        // The client is not allowed to change the whoseTurn
        // The client is not allowed to change the gameRunning
        this.pileList = tgo.pilesList;
        for (Player player:playerList){
            player.setHandCards(tgo.playerCards.get(player.getPlayerName()));
        }
    }

    public void updateCurrentPlayer() {
        String newPlayer = onePlayerFurther(whoseTurn);
        Player playerObject = playerList.get(findPlayerInPlayerList(newPlayer));

        //Failsafe counter
        int cnt = 0;
        while (playerObject.getNoOfCards()<1 && cnt <= playerList.size()){
            newPlayer = onePlayerFurther(newPlayer);
            playerObject = playerList.get(findPlayerInPlayerList(newPlayer));
            cnt += 1;
        }

        whoseTurn = newPlayer;
    }
    public String onePlayerFurther(String oldPlayer){
        int oldIndex = findPlayerInPlayerList(oldPlayer);
        int newIndex = (oldIndex+1) % playerList.size();

        return playerList.get(newIndex).getPlayerName();
    }


    private int findPlayerInPlayerList(String oldPlayer) {
        int len = playerList.size();
        for (int i = 0; i < len; i++){
            if (oldPlayer.equals(playerList.get(i).getPlayerName())){
                return i;
            }
        }
        return -200;

    }
    
    public void checkWin() {
        if (deck.getNoOfCards() == 0) {
            for (Player player : playerList) {
                if (player.getNoOfCards() != 0) {
                    return;
                }
            }
            gameStatus.setGameWon(true);
        }
    }

    public void draw(){
        Player playerObject = playerList.get(findPlayerInPlayerList(whoseTurn));
        playerObject.fillCards(fillUpToNoOfCards, deck);
    }


    public void onGameTerminated(){

        for(Player player:playerList){
            userService.setStatusInRepo(player.getId(), UserStatus.READY);

            if(gameStatus.getGameWon()){
                userService.updateScore(player, 100);
            }
            else if (gameStatus.getGameLost()){
                userService.updateScore(player,100 - remainingCards());
            }
        }
    }
    public int remainingCards(){
        int cardsInGame = 0;
        cardsInGame = this.deck.getNoOfCards();
        for(Player pl:playerList){
            cardsInGame += pl.getNoOfCards();
        }
        return cardsInGame;
    }

    public List<Player> getListOfPlayers() {return this.playerList;}

    public List<Pile> getPileList(){return this.pileList;}

    public GameStatus getGameStatus(){return this.gameStatus;}

    public String getWhoseTurn(){return this.whoseTurn;}

    public int getNoOfCardsOnDeck() {return this.deck.getNoOfCards();}

    public int getFillUpToNoOfCards(){return this.fillUpToNoOfCards;}

    public Deck getDeck(){return this.deck;}

}
