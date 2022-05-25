package ch.uzh.ifi.hase.soprafs22.controller;


import ch.uzh.ifi.hase.soprafs22.entity.*;
import ch.uzh.ifi.hase.soprafs22.service.GameService;
import com.solidfire.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ch.uzh.ifi.hase.soprafs22.service.UserService;



/**
 * WebSocket endpoints
 */
@Controller
public class WebSocketController {
    private final UserService userService;
    private WaitingRoom waitingRoom = new WaitingRoom();
    private final GameService gameService;
    private Game game = null;
    private int cnt = 0;


    WebSocketController(UserService userService, GameService gameService) { //TODO check only one userRepository
        this.userService = userService;
        this.gameService = gameService;
    }

    @MessageMapping("/game")
    @SendTo("/topic/players")
    public String addPlayersInWaitingroom(String userData){
        Gson gson = new Gson();
        String userString = gson.fromJson(userData, String.class);
        User userObject = userService.getUserByUsername(userString);
        Player newPlayer = new Player(userObject.getUsername(), userObject.getId());
        waitingRoom.addPlayer(newPlayer);
        String json = new Gson().toJson(waitingRoom.getPlayerNames());
        return json;
    }

    @MessageMapping("/getPlayers")
    @SendTo("/topic/getPlayers")
    public String getPlayers(){
        String json = new Gson().toJson(waitingRoom.getPlayerNames());
        return json;
    }

    @MessageMapping("/leave")
    @SendTo("/topic/players")
    public String removePlayerFromWaitingRoom(String userData){
        Gson gson = new Gson();
        String userString = gson.fromJson(userData, String.class);
        waitingRoom.removePlayer(userString);
        String json = new Gson().toJson(waitingRoom.getPlayerNames());
        return json;
    }

    @MessageMapping("/clearWaitingRoom")
    @SendTo("/topic/clearWaitingRoom")
    public String removeAllPlayerNamesFromWaitingRoom(){
        waitingRoom.removeAllPlayerNames();
        String json = new Gson().toJson(waitingRoom.getPlayerNames());
        return json;
    }

    @MessageMapping ("/isRunning")
    @SendTo("/topic/isRunning")
    public String isRunning(){
        if (game == null) {
            return new Gson().toJson(false);
        }
        TransferGameObject tgo = gameService.ConvertGameIntoTransferObject(game);
        String json = new Gson().toJson(tgo.gameRunning);
        return json;
    }


    @MessageMapping ("/start")
    @SendTo("/topic/start")
    public String startGame(){ // TODO Think whether waitingRoom list needs to get players again from user
        game = new Game();
        cnt = 0;
        game.startGame(waitingRoom.getPlayerList(), userService);
        TransferGameObject tgo = gameService.ConvertGameIntoTransferObject(game);   //
        String json = new Gson().toJson(tgo);
        return json;
    }

    @MessageMapping ("/discard")
    @SendTo("/topic/game")
    public String discard(String jsonTGO){
        //Transform to TGO
        Gson g = new Gson();
        TransferGameObject tgo = g.fromJson(jsonTGO, TransferGameObject.class);
        game.updateGameFromTGOInformation(tgo);
        game.checkWin();
        TransferGameObject tgo1 = gameService.ConvertGameIntoTransferObject(game);
        String json = new Gson().toJson(tgo1);
        return json;
    }

    @MessageMapping ("/draw")
    @SendTo("/topic/game")
    public String draw(){
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
        boolean doITidyUp = false;
        if (cnt == 0){
            cnt++;
            doITidyUp = true;
            game.onGameTerminated();
        }


        if(game.getGameStatus().getGameWon()){
            if (doITidyUp){
                waitingRoom.removeAllPlayers();
                game = null;
            }
            return new Gson().toJson("won");
        }
        else if(game.getGameStatus().getGameLost()){
            if (doITidyUp){
                waitingRoom.removeAllPlayers();
                game = null;
            }
            return new Gson().toJson("lost");
        }
        else if(game.getGameStatus().getUserLeft()) {
            if (doITidyUp){
                waitingRoom.removeAllPlayers();
                game = null;
            }
            return new Gson().toJson("left");
        }
        else{
            return new Gson().toJson("oh no");

        }

    }


}
