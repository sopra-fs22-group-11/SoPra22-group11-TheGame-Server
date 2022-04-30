package ch.uzh.ifi.hase.soprafs22.controller;


import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


//@WebMvcTest(WebSocketController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketControllerTest {

    //  @Autowired
    //  private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // @LocalServerPort
    @Value("${local.server.port}")
    private int port;
    private WebSocketStompClient webSocketStompClient;


    //TODO /hello

    //TODO /game

    //TODO /start

    @Test
    public void testStartEndpoint() throws Exception{

 /*
        Player player1 = new Player("player1", 1L);
        Player player2 = new Player("player2", 1L);


        List<Player> allPlayers ;
        allPlayers.add(player1);
        allPlayers.add(player2);
        given(waitingRoom.getPlayerList()).willReturn(allUsers);
*/
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(1);

        this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));


        StompSession session = webSocketStompClient
                .connect(String.format("ws://localhost:%d/ws", this.port), new StompSessionHandlerAdapter() {
                })
                .get(1, TimeUnit.SECONDS);

//subscription
        session.subscribe("/topic/start", new StompFrameHandler() {

            @Override
            public Class<String> getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                blockingQueue.add((String) payload);
            }
        });
        System.out.println("Port: " + this.port);
//sending request
        session.send("/app/start", null);

        System.out.println("blockingQueue: "+ blockingQueue);
        //  assertEquals(, blockingQueue.poll(1, TimeUnit.SECONDS));
    }

    //TODO /discard

    //TODO /draw

    //TODO /gameLost

    //TODO /gameLeft

    //TODO /gameStatus


}
