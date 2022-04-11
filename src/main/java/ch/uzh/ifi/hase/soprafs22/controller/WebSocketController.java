package ch.uzh.ifi.hase.soprafs22.controller;


import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    WebSocketController(UserService userService) {
        this.userService = userService;
    }


    @MessageMapping("/hello")
    @SendTo("/topic/players")
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

}
