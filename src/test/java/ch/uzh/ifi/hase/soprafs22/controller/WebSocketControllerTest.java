package ch.uzh.ifi.hase.soprafs22.controller;


import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.*;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import com.solidfire.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;


//@WebMvcTest(WebSocketController.class)


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class WebSocketControllerTest {

    //  @Autowired
    //  private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    //@MockBean
    //private GameService gameService;

    // @LocalServerPort
    @Value("${local.server.port}")
    private int port;
    private WebSocketStompClient webSocketStompClient;
    private StompSession session;
    List<String> list = new ArrayList<>();




    @BeforeEach
    void setup() throws InterruptedException, ExecutionException, TimeoutException {
        this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));


        webSocketStompClient.setMessageConverter(new StringMessageConverter());
        //webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        Thread.sleep(1000);

        session = webSocketStompClient
                .connect(String.format("ws://localhost:%d/ws", this.port), new StompSessionHandlerAdapter() {
                    @Override
                    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                        throw new RuntimeException("Failure in WebSocket handling", exception);
                    }
                })
                .get(1, TimeUnit.SECONDS);


    }

    @Test
    void testStartEndpoint() throws Exception{
        cleanGame();


 /*
        Player player1 = new Player("player1", 1L);
        Player player2 = new Player("player2", 1L);
        List<Player> allPlayers ;
        allPlayers.add(player1);
        allPlayers.add(player2);
        given(waitingRoom.getPlayerList()).willReturn(allUsers);
*/
        //Setup before each
        // this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
        //         List.of(new WebSocketTransport(new StandardWebSocketClient()))));
//
//
        // //BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(1);
        // List<String> list = new ArrayList<>();
//
        // webSocketStompClient.setMessageConverter(new StringMessageConverter());
        // Thread.sleep(1000);
//
//
        // StompSession session = webSocketStompClient
        //         .connect(String.format("ws://localhost:%d/ws", this.port), new StompSessionHandlerAdapter() {
        //         })
        //         .get(1, TimeUnit.SECONDS);

//subscription
        session.subscribe("/topic/start", new StompFrameHandler() {

            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.add(pl);
            }
        });
        System.out.println("Port: " + this.port);
//sending request

        session.send("/app/start", null);
        Thread.sleep(1000);


        //System.out.println("blockingQueue: "+ blockingQueue);
        System.out.println(list.get(0));
        //assertEquals(, blockingQueue.poll(1, TimeUnit.SECONDS));
        Gson g = new Gson();
        TransferGameObject tgo = g.fromJson(list.get(0), TransferGameObject.class);
        assertEquals(tgo.gameRunning, true);

        // {"noCardsOnDeck":84,"whoseTurn":"Anna","pilesList":[{"topCard":{"value":100},"direction":"TOPDOWN"},
        // {"topCard":{"value":100},"direction":"TOPDOWN"},{"topCard":{"value":1},"direction":"DOWNUP"},
        // {"topCard":{"value":1},"direction":"DOWNUP"}],"playerCards":{"Peter":[{"value":39},{"value":92},{"value":78},{"value":80},{"value":44},{"value":23},{"value":46}],
        // "Anna":[{"value":58},{"value":91},{"value":75},{"value":15},{"value":93},{"value":52},{"value":63}]},"gameRunning":true}
        // Maybe we can access the gameStauts variable in the game object and  just check if "gameRunning" is true
        // so that we don't have to mock all the cards
    }

    @Test
    void testGameWatinigroomEndpoint() throws Exception{
        cleanGame();


        session.subscribe("/topic/players", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.add(pl);
            }

        });
        Thread.sleep(500);


        System.out.println("Port: " + this.port);
//sending request
        User testUser = new User();
        testUser.setUsername("TestUser");
        testUser.setPassword("aa");
        testUser.setId(1L);
        testUser.setStatus(UserStatus.READY);
        testUser.setScore(0);
        testUser.setToken("aaaa");
        given(userService.getUserByUsername("TestUser")).willReturn(testUser);


        session.send("/app/game", "TestUser");
        Thread.sleep(1000);
        System.out.println("received"+ list.get(0));

        Gson g = new Gson();
        List l = g.fromJson(list.get(0), List.class);
        assertEquals(l.get(0), "TestUser");

    }

    @Test
    void testLostEndpoint() throws Exception {
        cleanGame();


        session.subscribe("/topic/game", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.add(pl);
            }

        });
        setUpRunningGame();
        Thread.sleep(500);

        session.send("/app/gameLost", null);
        Thread.sleep(500);


        System.out.println("received: " +list.get(0));

        Gson gson = new Gson();
        TransferGameObject tg = gson.fromJson(list.get(0), TransferGameObject.class);
        assertEquals(false, tg.gameRunning);





    }

    @Test
    void gameStatusTest() throws InterruptedException {
        cleanGame();

        session.subscribe("/topic/status", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.add(pl);
            }
        });
        setUpRunningGame();
        session.send("/app/gameLeft", null);
        Thread.sleep(500);
        session.send("/app/gameStatus", null);
        Thread.sleep(500);

        Gson g = new Gson();
        String reason = g.fromJson(list.get(0), String.class);
        assertEquals(reason, "left");


    }

    @Test
    void gameIsRunningTest() throws InterruptedException {
        cleanGame();
        session.subscribe("/topic/isRunning", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.add(pl);
            }
        });
        setUpRunningGame();
        session.send("/app/isRunning", null);
        Thread.sleep(500);

        Gson gson = new Gson();
        boolean b = gson.fromJson(list.get(0), boolean.class);
        assertEquals(true, b);


    }
    @Test
    @Order(1)
    void gameIsRunningFalseTest() throws InterruptedException {
        session.subscribe("/topic/isRunning", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.clear();
                list.add(pl);
            }
        });
        //setUpRunningGame();
        //session.send("/app/gameLeft", null);
        //Thread.sleep(500);
        session.send("/app/isRunning", null);
        Thread.sleep(500);

        Gson gson = new Gson();
        boolean tg = gson.fromJson(list.get(0), boolean.class);
        assertFalse(tg);


    }

    @Test
    void PlayerLeavesWaitingRoomTest() throws InterruptedException {
        cleanGame();
        session.subscribe("/topic/players", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.clear();
                list.add(pl);
            }
        });
        User testUser = new User();
        testUser.setUsername("TestUser");
        testUser.setPassword("aa");
        testUser.setId(1L);
        testUser.setStatus(UserStatus.READY);
        testUser.setScore(0);
        testUser.setToken("aaaa");

        User testUser1 = new User();
        testUser1.setUsername("TestUser1");
        testUser1.setPassword("aa");
        testUser1.setId(2L);
        testUser1.setStatus(UserStatus.READY);
        testUser1.setScore(0);
        testUser1.setToken("aaaaf");
        given(userService.getUserByUsername("TestUser")).willReturn(testUser);
        given(userService.getUserByUsername("TestUser1")).willReturn(testUser1);

        session.send("/app/clearWaitingRoom", null);
        session.send("/app/game", "TestUser");
        Thread.sleep(500);
        session.send("/app/game", "TestUser1");
        Thread.sleep(500);
        session.send("/app/leave","TestUser");
        Thread.sleep(500);

        Gson g = new Gson();
        List l = g.fromJson(list.get(0), List.class);
        assertEquals("TestUser1", l.get(0));


    }

    @Test
    void discardDrawTest() throws InterruptedException { //Tests that change of turn is handled correctly
        cleanGame();

        List<String> startList = new ArrayList<>();

        session.subscribe("/topic/game", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.clear();
                list.add(pl);
            }
        });

        session.subscribe("/topic/start", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                startList.add(pl);
            }
        });
        setUpRunningGame();
        Gson gson = new Gson();
        TransferGameObject tg = gson.fromJson(startList.get(0), TransferGameObject.class);
        String whoseTurn = tg.whoseTurn;

        tg.playerCards.get("TestUser").remove(0);
        tg.playerCards.get("TestUser").remove(0);



        session.send("/app/discard", new Gson().toJson(tg));
        Thread.sleep(500);
        Gson gson1 = new Gson();
        TransferGameObject tg1 = gson1.fromJson(list.get(0), TransferGameObject.class);
        //assertEquals(tg.playerCards,tg1.playerCards );
        assertThat(tg.playerCards).usingRecursiveComparison().isEqualTo(tg1.playerCards);

        session.send("/app/draw", null);
        Gson gson2 = new Gson();
        TransferGameObject tg2 = gson2.fromJson(list.get(0), TransferGameObject.class);
        String expected;
        if(whoseTurn == "TestUser"){  // check that turn change is correct
            expected = "TestUser1";
        }
        else{
            expected = whoseTurn;
        }
        assertEquals(expected,tg2.whoseTurn);




    }
    @Test
    @Order(2)
    void getPlayersTest() throws InterruptedException {
        session.subscribe("/topic/getPlayers", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.add(pl);
            }
        });
        putPlayersInWaitingRoom();
        session.send("/app/getPlayers", null);
        Thread.sleep(500);

        Gson gson = new Gson();
        List l = gson.fromJson(list.get(0), List.class);
        assertEquals(2,l.size());


    }

    @Test
    //@Order (6)
    void gameStatusWonTest() throws InterruptedException {
        cleanGame();
        List<String> startList = new ArrayList<>();

        session.subscribe("/topic/start", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame1");
                startList.clear();
                startList.add(pl);
            }
        });

        session.subscribe("/topic/game", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame1");
                startList.clear();
                startList.add(pl);
            }
        });

        session.subscribe("/topic/status", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame2");
                list.add(pl);
            }
        });

        setUpRunningGame();
        Thread.sleep(200);
        Gson gson = new Gson();
        for(int i=0; i<13;i++) {
            TransferGameObject tg = gson.fromJson(startList.get(0), TransferGameObject.class);
            Map<String, List<Card>> dictionary = new HashMap<>();
            List<Card> emptyCards = new ArrayList<Card>();
            dictionary.put("TestUser", emptyCards);
            dictionary.put("TestUser1", emptyCards);
            tg.playerCards = dictionary;
            session.send("/app/discard", new Gson().toJson(tg));
            System.out.println("nach app discard");
            Thread.sleep(200);
            session.send("/app/draw",null);
            Thread.sleep(200);


        }

        session.send("/app/gameStatus", null);
        Thread.sleep(500);

        Gson g = new Gson();
        String reason = g.fromJson(list.get(0), String.class);
        assertEquals("won", reason);


    }

    public void putPlayersInWaitingRoom() throws InterruptedException {
        User testUser = new User();
        testUser.setUsername("TestUser");
        testUser.setPassword("aa");
        testUser.setId(1L);
        testUser.setStatus(UserStatus.READY);
        testUser.setScore(0);
        testUser.setToken("aaaa");

        User testUser1 = new User();
        testUser1.setUsername("TestUser1");
        testUser1.setPassword("aa");
        testUser1.setId(2L);
        testUser1.setStatus(UserStatus.READY);
        testUser1.setScore(0);
        testUser1.setToken("aaaaf");
        given(userService.getUserByUsername("TestUser")).willReturn(testUser);
        given(userService.getUserByUsername("TestUser1")).willReturn(testUser1);



        session.send("/app/game", "TestUser");
        Thread.sleep(500);
        session.send("/app/game", "TestUser1");
        Thread.sleep(500);
    }
    @Test
    void gameStatusLostTest() throws InterruptedException {
        cleanGame();
        session.subscribe("/topic/status", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.add(pl);
            }
        });
        setUpRunningGame();
        session.send("/app/gameLost", null);
        Thread.sleep(500);
        session.send("/app/gameStatus", null);
        Thread.sleep(500);

        Gson g = new Gson();
        String reason = g.fromJson(list.get(0), String.class);
        assertEquals(reason, "lost");


    }

    @Test
    void gameStatusLeftTest() throws InterruptedException {
        cleanGame();
        session.subscribe("/topic/status", new StompFrameHandler() {


            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                System.out.println(headers);
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                String pl = (String) payload;
                //blockingQueue.add((String) payload);
                System.out.println("in handle Frame");
                list.add(pl);
            }
        });
        setUpRunningGame();
        session.send("/app/gameLeft", null);
        Thread.sleep(500);
        session.send("/app/gameStatus", null);
        Thread.sleep(500);

        Gson g = new Gson();
        String reason = g.fromJson(list.get(0), String.class);
        assertEquals(reason, "left");



    }




    public void cleanGame() throws InterruptedException {
        session.send("/app/gameLeft", null);
        Thread.sleep(200);

        session.send("/app/gameStatus", null);
        Thread.sleep(200);



    }

    public void setUpRunningGame() throws InterruptedException {

        User testUser = new User();
        testUser.setUsername("TestUser");
        testUser.setPassword("aa");
        testUser.setId(1L);
        testUser.setStatus(UserStatus.READY);
        testUser.setScore(0);
        testUser.setToken("aaaa");

        User testUser1 = new User();
        testUser1.setUsername("TestUser1");
        testUser1.setPassword("aa");
        testUser1.setId(2L);
        testUser1.setStatus(UserStatus.READY);
        testUser1.setScore(0);
        testUser1.setToken("aaaaf");
        given(userService.getUserByUsername("TestUser")).willReturn(testUser);
        given(userService.getUserByUsername("TestUser1")).willReturn(testUser1);


        session.send("/app/game", "TestUser");
        Thread.sleep(500);
        session.send("/app/game", "TestUser1");
        Thread.sleep(500);
        session.send("/app/start", null);
        Thread.sleep(500);
    }

}