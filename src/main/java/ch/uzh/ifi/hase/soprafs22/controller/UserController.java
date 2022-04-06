package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API User to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create User
        User createdUser = userService.createUser(userInput);
        //createdUser.setStatus(UserStatus.READY);
        userService.setStatusInRepo(createdUser.getId(), UserStatus.READY);

        // convert internal representation of User back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
    }



    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
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


    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updateUser(@RequestBody UserPostDTO userPostDTO, @PathVariable long userId) {
        // convert API User to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        User userDB = userService.getUserById(userId);

        if (userDB ==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The user with userId %s was not found.", userId));
        }

        if (userInput.getUsername() != null) {
            userDB.setUsername(userInput.getUsername());
        }

        if (userInput.getUsername() != null) {
            userDB.setUsername(userInput.getUsername());
        }

        userService.saveUpdate(userDB);
    }

    @PostMapping("/session")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        List<User> users = userService.getUsers();

        for (int i = 0; i< users.size(); i++) {
            if (userInput.getUsername().equals(users.get(i).getUsername())) {
                if (userInput.getPassword().equals(users.get(i).getPassword())){
                    User user = users.get(i);
                    userService.setStatusInRepo(user.getId(), UserStatus.READY);
                    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is wrong!");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Given username is not found. Please register first.");
    }

    @GetMapping("/session/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void logout_updateStatus(@PathVariable long userId) {
        userService.setStatusInRepo(userId, UserStatus.OFFLINE);
    }
}



