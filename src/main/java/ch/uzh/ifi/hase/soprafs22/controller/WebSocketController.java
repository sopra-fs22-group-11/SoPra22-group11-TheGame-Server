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
    public String addPlayersInWaitingroom(UserPostDTO userData){
        User userObject = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userData);
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




   //@MessageMapping("/game")
   //@SendTo("/topic/players")
   //public List<Player> getPlayersInWaitingroom(){
   //    System.out.println("in getPlayersInWaitingroom method");
   //    return waitingRoom.getPlayerList();

   //}


}
