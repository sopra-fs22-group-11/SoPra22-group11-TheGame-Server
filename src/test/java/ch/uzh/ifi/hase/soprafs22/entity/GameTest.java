package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Directions;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    //TODO tests not ok yet

    //test startGame with 2 players
    @Test
    public void startGameTestWithTwo() {
        Game game = new Game();

        Player player1 = new Player("player1",  new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);

        game.startGame(pl, userService);

        //whoseTurn
        assertEquals(player1.getPlayerName(), game.getWhoseTurn());
        //fillUpToNoOfCards
        assertEquals(7,game.getFillUpToNoOfCards());
        //playerList
        assertEquals(2,game.getListOfPlayers().size());
        //handcards
        for (Player player: pl){
            assertEquals(7,player.getNoOfCards());
        }

    }

    //test startGame with 2 < number of players < 6
    @Test
    public void startGameTestMoreThanTwo() {
        Game game = new Game();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        Player player4 = new Player("player4",new Long("4"));
        Player player5 = new Player("player5",new Long("5"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);
        pl.add(player4);
        pl.add(player5);

        game.startGame(pl, userService);

        //whoseTurn
        assertEquals(player1.getPlayerName(), game.getWhoseTurn());
        //fillUpToNoOfCards
        assertEquals(6,game.getFillUpToNoOfCards());
        //playerList
        assertEquals(5,game.getListOfPlayers().size());
        //handcards
        for (Player player: pl){
            assertEquals(6,player.getNoOfCards());
        }
    }

    //TODO test updateGamefromTGOInformation
    //
    @Test
    public void updateGameFromTGOInformationTest(){
        Game game = new Game();
        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));

        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);


        List<Pile> pileList = new ArrayList<>();
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.TOPDOWN));
        pileList.add(new Pile(Directions.DOWNUP));
        pileList.add(new Pile(Directions.DOWNUP));
        pileList.get(2).setTopCard(new Card(79));
        TransferGameObject tgo = new TransferGameObject();
        tgo.whoseTurn = "player2";
        tgo.gameRunning = true;
        tgo.pilesList = pileList;
        List<Card> cards1 = new ArrayList<>();
        cards1.add(new Card(3)); cards1.add(new Card(4));cards1.add(new Card(5));
        List<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(6)); cards2.add(new Card(7));cards2.add(new Card(8));
        List<Card> cards3 = new ArrayList<>();
        cards3.add(new Card(9)); cards3.add(new Card(10));cards3.add(new Card(11));
        tgo.playerCards  = new HashMap<>();
        tgo.playerCards.put("player1", cards1);
        tgo.playerCards.put("player2", cards2);
        tgo.playerCards.put("player3", cards3);

        game.startGame(pl, userService);

        game.updateGameFromTGOInformation(tgo);

        assertEquals(true, game.getGameStatus().getGameRunning());
        assertEquals("player1", game.getWhoseTurn());
        assertEquals(pileList, game.getPileList());

        assertEquals(cards1,game.getListOfPlayers().get(0).getHandCards());
        assertEquals(cards2,game.getListOfPlayers().get(1).getHandCards());
        assertEquals(cards3,game.getListOfPlayers().get(2).getHandCards());

    }

    //test updateCurrentPlayer
    //everyone has cards
    @Test
    public void updateCurrentPlayerTest() {
        Game game = new Game();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        Player player4 = new Player("player4",new Long("4"));
        Player player5 = new Player("player5",new Long("5"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);
        pl.add(player4);
        pl.add(player5);

        game.startGame(pl, userService);

        game.updateCurrentPlayer();
        assertEquals(player2.getPlayerName(), game.getWhoseTurn());

        game.updateCurrentPlayer();
        assertEquals(player3.getPlayerName(), game.getWhoseTurn());

        game.updateCurrentPlayer();
        assertEquals(player4.getPlayerName(), game.getWhoseTurn());

        game.updateCurrentPlayer();
        assertEquals(player5.getPlayerName(), game.getWhoseTurn());

        game.updateCurrentPlayer();
        assertEquals(player1.getPlayerName(), game.getWhoseTurn());

    }

    //test updateCurrentPlayer
    //next player has no cards
    @Test
    public void updateCurrentPlayerNoCardsTest1() {
        Game game = new Game();

        List<Card>  emptyCardList = new ArrayList<>();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        Player player4 = new Player("player4",new Long("4"));
        Player player5 = new Player("player5",new Long("5"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);
        pl.add(player4);
        pl.add(player5);

        game.startGame(pl, userService);
        player2.setHandCards(emptyCardList);
        assertEquals(0, player2.getNoOfCards());

        game.updateCurrentPlayer();
        assertEquals(player3.getPlayerName(), game.getWhoseTurn());

    }

    // test updateCurrentPlayer
    //next 2 player have no cards
    @Test
    public void updateCurrentPlayerNoCardsTest2() {
        Game game = new Game();

        List<Card>  emptyCardList = new ArrayList<>();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        Player player4 = new Player("player4",new Long("4"));
        Player player5 = new Player("player5",new Long("5"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);
        pl.add(player4);
        pl.add(player5);

        game.startGame(pl, userService);
        player2.setHandCards(emptyCardList);
        assertEquals(0, player2.getNoOfCards());

        player3.setHandCards(emptyCardList);
        assertEquals(0, player3.getNoOfCards());

        game.updateCurrentPlayer();
        assertEquals(player4.getPlayerName(), game.getWhoseTurn());

    }

    //test onePlayerFurther
    @Test
    public void onePlayerFurtherTest() {
        Game game = new Game();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        Player player4 = new Player("player4",new Long("4"));
        Player player5 = new Player("player5",new Long("5"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);
        pl.add(player4);
        pl.add(player5);

        game.startGame(pl, userService);


        assertEquals(player2.getPlayerName(), game.onePlayerFurther(player1.getPlayerName()));

        assertEquals(player3.getPlayerName(), game.onePlayerFurther(player2.getPlayerName()));

        assertEquals(player4.getPlayerName(), game.onePlayerFurther(player3.getPlayerName()));

        assertEquals(player5.getPlayerName(), game.onePlayerFurther(player4.getPlayerName()));

        assertEquals(player1.getPlayerName(), game.onePlayerFurther(player5.getPlayerName()));

    }

    //test checkWin
    //no player has any cards left and deck is empty --> gamestatus gameWon true
    @Test
    public void checkWinNoCardsNoDeckTest(){
        Game game = new Game();

        List<Card>  emptyCardList = new ArrayList<>();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);

        game.startGame(pl, userService);
        player1.setHandCards(emptyCardList);
        player2.setHandCards(emptyCardList);
        player3.setHandCards(emptyCardList);



        for(int i = 0;i<80; i++){
            game.getDeck().pop();
        }

        game.checkWin();
        assertEquals(true, game.getGameStatus().getGameWon());

    }

    // test checkWin
    //some players have cards left and deck is empty --> gamestatus gameWon false
    @Test
    public void checkWinOnlyNoDeckTest(){
        Game game = new Game();

        List<Card>  emptyCardList = new ArrayList<>();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);

        game.startGame(pl, userService);
        player1.setHandCards(emptyCardList);
        player2.setHandCards(emptyCardList);



        for(int i = 0;i<80; i++){
            game.getDeck().pop();
        }

        game.checkWin();
        assertEquals(false, game.getGameStatus().getGameWon());

    }

    // test checkWin
    //deck is not empty --> gamestatus gameWon false
    @Test
    public void checkWinDeckNonEmptyTest(){
        Game game = new Game();

        List<Card>  emptyCardList = new ArrayList<>();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);

        game.startGame(pl, userService);



        game.checkWin();
        assertEquals(false, game.getGameStatus().getGameWon());

    }


    //test draw
    //current player has no cards, should get 7 cards
    @Test
    public void drawNoCardsTwoPlayerTest(){
        Game game = new Game();

        List<Card>  emptyCardList = new ArrayList<>();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);


        game.startGame(pl, userService);
        player1.setHandCards(emptyCardList);
        assertEquals(0, player1.getNoOfCards());
        game.draw();
        assertEquals(7, player1.getNoOfCards());

    }

    //test draw
    //current player has no cards,should get 6 cards
    @Test
    public void drawNoCardsMorePlayerTest(){
        Game game = new Game();

        List<Card>  emptyCardList = new ArrayList<>();

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);

        game.startGame(pl, userService);
        player1.setHandCards(emptyCardList);
        assertEquals(0, player1.getNoOfCards());
        game.draw();
        assertEquals(6, player1.getNoOfCards());

    }

    //test draw
    // should fill up to 7 cards, but already has some cards
    @Test
    public void drawSomeCardsTest(){
        Game game = new Game();

        List<Card>  cardList = new ArrayList<>();
        Card c1 = game.getDeck().pop();
        Card c2 = game.getDeck().pop();
        cardList.add(c1);
        cardList.add(c2);

        Player player1 = new Player("player1",new Long("1"));
        Player player2 = new Player("player2",new Long("2"));
        Player player3 = new Player("player3",new Long("3"));
        List<Player> pl = new ArrayList<>();
        pl.add(player1);
        pl.add(player2);
        pl.add(player3);

        game.startGame(pl, userService);
        player1.setHandCards(cardList);
        assertEquals(2, player1.getNoOfCards());
        game.draw();
        assertEquals(6, player1.getNoOfCards());

    }

/*

//
       // //Another functionality would take care of this - namely playerService
       // player1.setGameCount(0);
       // player2.setGameCount(0);
//

//
       //Game game = Game.initializeGame(pl, userService);

        User user1 = new User();
        User user2 = new User();

        //Another functionality would take care of this - namely playerService
        user1.setGameCount(0);
        user2.setGameCount(0);

        List<User> pl = new ArrayList<>();
        pl.add(user1);
        pl.add(user2);

        //Game game = Game.initializeGame(pl, userService);
*/
    }
/*
    @Test
    public void Game_StatusCheck() {
        List<Pile> pileList = new ArrayList<>();
        int fillUpToNoOfCards;

        testUser = new User();
        testUser.setId(1L);
        testUser.setPassword("testName");
        testUser.setUsername("testUsername");
        testUser.setStatus(UserStatus.READY);

        List<User> userList = new ArrayList<>();
        userList.add(testUser);
        userList.add(testUser);

        // initially status of each user is ready
        for (User user:userList) {
            assertEquals(user.getStatus(), UserStatus.READY);
        }

        Player testPlayer = new Player(testUser.getUsername(), testUser.getId());
        Game game = new Game();
        game.addPlayer(testPlayer);
        game.addPlayer(testPlayer);
        game.startGame();

        //after game started status of each user changes to ingame
        for (User user:userList) {
            assertEquals(user.getStatus(), UserStatus.INGAME);
        }

 */



