package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
public class PlayerController {

    private final PlayerService playerService;

    PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PlayerGetDTO createPlayer(@RequestBody PlayerPostDTO playerPostDTO) {
        // convert API player to internal representation
        Player playerInput = DTOMapper.INSTANCE.convertPlayerPostDTOtoEntity(playerPostDTO);

        // create player
        Player createdPlayer = playerService.createPlayer(playerInput);

        // convert internal representation of player back to API
        return DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(createdPlayer);
    }



    @GetMapping("/players")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PlayerGetDTO> getPlayers() {
        // fetch all players in the internal representation
        List<Player> players = playerService.getPlayers();
        List<PlayerGetDTO> playerGetDTOS = new ArrayList<>();

        // convert each player to the API representation
        for (Player player : players) {
            playerGetDTOS.add(DTOMapper.INSTANCE.convertEntityToPlayerGetDTO(player));
        }
        return playerGetDTOS;
    }


    @PutMapping("/players/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updatePlayer(@RequestBody PlayerPostDTO playerPostDTO, @PathVariable long playerId) {
        // convert API player to internal representation
        Player playerInput = DTOMapper.INSTANCE.convertPlayerPostDTOtoEntity(playerPostDTO);
        Player playerDB = playerService.getPlayerById(playerId);

        if (playerDB==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The player with playerId %s was not found.", playerId));
        }

        if (playerInput.getUsername() != null) {
            playerDB.setUsername(playerInput.getUsername());
        }

        if (playerInput.getUsername() != null) {
            playerDB.setUsername(playerInput.getUsername());
        }

        playerService.saveUpdate(playerDB);
    }

    @PutMapping("/session")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void loginPlayer(@RequestBody PlayerPostDTO playerPostDTO) {
        Player playerInput = DTOMapper.INSTANCE.convertPlayerPostDTOtoEntity(playerPostDTO);

        List<Player> players = playerService.getPlayers();

        for (int i = 0; i<players.size(); i++) {
            if (playerInput.getUsername().equals(players.get(i).getUsername())) {
                if (playerInput.getPassword().equals(players.get(i).getPassword())){
                    Player player = players.get(i);
                    player.setStatus(PlayerStatus.READY); // change status to READY/ONLINE
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is wrong!");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please register first.");
    }



}



