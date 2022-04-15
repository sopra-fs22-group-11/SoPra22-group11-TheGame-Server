package ch.uzh.ifi.hase.soprafs22.controller;


import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.WaitingRoom;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.websocket.ResponseMessage;
import com.solidfire.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
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

    WebSocketController(UserService userService) {
        this.userService = userService;
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
    public ResponseMessage addPlayersInWaitingroom(UserPostDTO userData){
        User userObject = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userData);
        Player newPlayer = new Player(userObject.getUsername(), userObject.getId());
        waitingRoom.addPlayer(newPlayer);
        String json = new Gson().toJson(waitingRoom.getPlayerList());
        System.out.println(json);
        System.out.println("in addlpayersInWatitingRoom method");
        return new ResponseMessage(HtmlUtils.htmlEscape(json));

        //return waitingRoom.getPlayerList();
    }

   //@MessageMapping("/game")
   //@SendTo("/topic/players")
   //public List<Player> getPlayersInWaitingroom(){
   //    System.out.println("in getPlayersInWaitingroom method");
   //    return waitingRoom.getPlayerList();

   //}


}
