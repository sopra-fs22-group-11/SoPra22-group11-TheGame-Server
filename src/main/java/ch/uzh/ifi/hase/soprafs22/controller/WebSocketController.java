package ch.uzh.ifi.hase.soprafs22.controller;


import ch.uzh.ifi.hase.soprafs22.entity.*;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import com.solidfire.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;



/**
 * WebSocket endpoints
 */
@Controller
public class WebSocketController {
    private final UserService userService;
    private WaitingRoom waitingRoom = new WaitingRoom();
    private final GameService gameService;
    private Game game;


    WebSocketController(UserService userService, GameService gameService) { //TODO check only one userRepository
        this.userService = userService;
        this.gameService = gameService;
    }


    @MessageMapping("/game")
    @SendTo("/topic/players")
    public String addPlayersInWaitingroom(String userData){
        //System.out.println(userData);
        Gson gson = new Gson();
        String userString = gson.fromJson(userData, String.class);
        User userObject = userService.getUserByUsername(userString);
        //System.out.println("the name is:"+userObject.getUsername());
        Player newPlayer = new Player(userObject.getUsername(), userObject.getId());
        waitingRoom.addPlayer(newPlayer);
        System.out.println(waitingRoom.getPlayerNames());
        String json = new Gson().toJson(waitingRoom.getPlayerNames());
        return json;

    }


    @MessageMapping ("/start")
    @SendTo("/topic/start")
    public String startGame(){ // TODO Think whether waitingRoom list needs to get players again from user
        game = new Game();
        System.out.println("In startgame() method");
        game.startGame(waitingRoom.getPlayerList(), userService);
        System.out.println(game.getNoOfCardsOnDeck());
        TransferGameObject tgo = gameService.ConvertGameIntoTransferObject(game);   //
        String json = new Gson().toJson(tgo);
        System.out.println(json);
        return json;
    }

    @MessageMapping ("/discard")
    @SendTo("/topic/game")
    public String discard(String jsonTGO){
        //Transform to TGO
        Gson g = new Gson();
        System.out.println("vor json in tgo umwandeln, jsontgo:"+ jsonTGO);
        TransferGameObject tgo = g.fromJson(jsonTGO, TransferGameObject.class);
        game.updateGameFromTGOInformation(tgo);
        game.checkWin();
        TransferGameObject tgo1 = gameService.ConvertGameIntoTransferObject(game);
        String json = new Gson().toJson(tgo1);
        return json;
    }

    @MessageMapping ("/draw")
    @SendTo("/topic/game")
    public String draw(){ // TODO we don't pass anything, server knows how to handle (TK)
        game.draw();
        game.updateCurrentPlayer();
        TransferGameObject tgo = gameService.ConvertGameIntoTransferObject(game);
        String json = new Gson().toJson(tgo);
        return json;
    } //TODO String etc can be void

    @MessageMapping ("/gameLost")
    @SendTo("/topic/game")
    public String lost(){
        game.getGameStatus().setGameLost(true);

        TransferGameObject tgo = gameService.ConvertGameIntoTransferObject(game);
        String json = new Gson().toJson(tgo);
        return json;
    }

    @MessageMapping ("/gameLeft")
    @SendTo("/topic/game")
    public String left(){
        game.getGameStatus().setUserLeft(true);

        TransferGameObject tgo = gameService.ConvertGameIntoTransferObject(game);
        String json = new Gson().toJson(tgo);
        return json;
    }


    @MessageMapping ("/gameStatus")
    @SendTo("/topic/status")
    public String gameStatus(){ // Should not return json, but a string lost/won/left
        game.onGameTerminated();

        if(game.getGameStatus().getGameWon()){
            waitingRoom.removeAllPlayers();
            game = null;
            return new Gson().toJson("won");
        }
        else if(game.getGameStatus().getGameLost()){
            waitingRoom.removeAllPlayers();
            game = null;
            return new Gson().toJson("lost");
        }
        else if(game.getGameStatus().getUserLeft()) {
            waitingRoom.removeAllPlayers();
            game = null;
            return new Gson().toJson("left");
        }
        else if(game.getGameStatus().getGameRunning()){
            waitingRoom.removeAllPlayers();
            game = null;
            return new Gson().toJson("running");
        }
        else{
            return new Gson().toJson("oh no");

        }

    }

}
