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

import java.util.ArrayList;
import java.util.List;


/**
 * WebSocket endpoints
 */
@Controller
public class WebSocketController {
    private final UserService userService;
    private final WaitingRoom waitingRoom = new WaitingRoom();
    private final GameService gameService;
    private Game game = new Game();


    WebSocketController(UserService userService, GameService gameService) { //TODO check only one userRepository
        this.userService = userService;
        this.gameService = gameService;
    }


    @MessageMapping("/hello")
    @SendTo("/topic/users")
    public List<UserGetDTO> getUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOS = new ArrayList<>();

        // convert each User to the API representation
        for (User user : users) {
            userGetDTOS.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOS;
    }

    @MessageMapping("/game")
    @SendTo("/topic/players")
    public String addPlayersInWaitingroom(String userData){
        System.out.println(userData);
        Gson gson = new Gson();
        User userObject = gson.fromJson(userData, User.class);
        System.out.println("the name is:"+userObject.getUsername());
        Player newPlayer = new Player(userObject.getUsername(), userObject.getId());
        waitingRoom.addPlayer(newPlayer);
        String json = new Gson().toJson(waitingRoom.getPlayerList());
        System.out.println(json);
        System.out.println("in addlpayersInWatitingRoom method");
        System.out.println("vor add player");
        //game.addPlayer(newPlayer);
        System.out.println("vor send game update");
        sendGameUpdate();
        System.out.println("vor return in addPlayersWatiningRoom");
        return json;

        //return waitingRoom.getPlayerList();
    }


    // TODO delete later when start ok
    @MessageMapping("/game1")
    @SendTo("/topic/gameObject")
    public String sendGameUpdate(){
        Game gameObject= game;
        System.out.println("vor tgo");
        TransferGameObject tgo = gameService.ConvertGameIntoTransferObject(gameObject);
        System.out.println("vor Gson");
        String json = new Gson().toJson(tgo);
        System.out.println(json);
        System.out.println("vor Return in sendGameUpdate");
        return json;
    }

    @MessageMapping ("/start")
    @SendTo("/topic/start")
    public String startGame(){ // TODO Think whether waitingRoom list needs to get players again from user
        Game gameObject= game;
        game.startGame(waitingRoom.getPlayerList(), userService);
        TransferGameObject tgo = gameService.ConvertGameIntoTransferObject(gameObject);
        String json = new Gson().toJson(tgo);


        return null;
    }

    @MessageMapping ("/discard")
    @SendTo("/topic/game")
    public String discard(String jsonTGO){
        //Transform to TGO
        Gson g = new Gson();
        System.out.println("vor json in tgo umwandeln, jsontgo:"+ jsonTGO);
        TransferGameObject tgo = g.fromJson(jsonTGO, TransferGameObject.class);


        game.updateGamefromTGOInformation(tgo);
        // call game.discard()


        return null;
    }

    @MessageMapping ("/draw")
    @SendTo("/topic/game")
    public String draw(String jsonOfListCards){
        return null;
    } //TODO String etc can be void

    @MessageMapping ("/gameLost")
    @SendTo("/topic/game")
    public String lost(){
        return null;
    }

    @MessageMapping ("/gameStatus")
    @SendTo("/topic/Status")
    public String gameStatus(){ // Should not return json, but a string lost/won/left
        return null;
    }







   //@MessageMapping("/game")
   //@SendTo("/topic/players")
   //public List<Player> getPlayersInWaitingroom(){
   //    System.out.println("in getPlayersInWaitingroom method");
   //    return waitingRoom.getPlayerList();

   //}


}