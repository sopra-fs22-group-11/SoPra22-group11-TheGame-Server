package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(UserController.class)
 class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        user.setUsername("Firstname Lastname");
        user.setPassword("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);

        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.getUsers()).willReturn(allUsers);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].status", is(user.getStatus().toString())));
    }

    @Test
    void givenUser_whenGetUserById_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("Firstname Lastname");
        user.setPassword("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);



        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.getUserById(1L)).willReturn(user);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users/{userId}",1L).contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                //    .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
    }

    @Test
    void givenUser_whenGetUserById_NotFound() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("Firstname Lastname");
        user.setPassword("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);



        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.getUserById(2L)).willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("The user with this id does not exist")));

        // when
        MockHttpServletRequestBuilder getRequest = get("/users/{userId}",2L).contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isNotFound());

    }

    @Test
    void givenUser_whenGetUserById_NotAuthorized() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("Firstname Lastname");
        user.setPassword("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("Test User");
        userPostDTO.setPassword("wrongPassword");

        // this mocks the UserService -> we define above what the userService should
        // return when getUsers() is called
        given(userService.getUserById(1L)).willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("The user with this id does not exist")));

        // when
        MockHttpServletRequestBuilder getRequest = get("/users/{userId}",1L).contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isBadRequest());

    }
    @Test
     void createUser_validInput_UserCreated() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("Test User");
        user.setPassword("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.OFFLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsername");

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));


        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
    }

    @Test
    void createUser_invalidInput_UserNotCreated() throws Exception {

        given(userService.createUser(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.CONFLICT,
                String.format("The username provided is not unique. Therefore, the User could not be created!")));

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsername");


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isConflict());
    }

    @Test
    void loginUser_validInput_UserLoggedIn() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setPassword("Test User");
        user.setUsername("testusername");
        user.setToken("1");
        user.setStatus(UserStatus.READY);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testusername");

        List<User> allUsers = Collections.singletonList(user);

        given(userService.getUsers()).willReturn(allUsers);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
    }

    @Test
    void loginUser_invalidPassword() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("Test User");
        user.setPassword("testusername");
        user.setToken("1");
        user.setStatus(UserStatus.OFFLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("Test User");
        userPostDTO.setPassword("wrongPassword");

        List<User> allUsers = Collections.singletonList(user);

        given(userService.getUsers()).willReturn(allUsers);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginUser_notRegistered() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("Test User");
        user.setPassword("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.OFFLINE);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("TestNotRegistered");
        userPostDTO.setPassword("testUsername");

        List<User> allUsers = Collections.singletonList(user);

        given(userService.getUsers()).willReturn(allUsers);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_success() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setPassword("Test User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.READY);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsername");

        given(userService.getUserById(user.getId())).willReturn(user);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUser_IdNotFound_fail() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setPassword("Test User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.READY);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsername");

        given(userService.getUserById(user.getId())).willReturn(null);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNotFound());
    }
    @Test
    void updateUser_NotAuthorized() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setPassword("Test User");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setStatus(UserStatus.READY);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("wrong Password");
        userPostDTO.setUsername("testUsername");

        given(userService.getUserById(user.getId())).willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("The user with this id does not exist")));


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isBadRequest());
    }


    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input
     * can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     *
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}