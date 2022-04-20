package ch.uzh.ifi.hase.soprafs22.service;


import ch.uzh.ifi.hase.soprafs22.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GameService {


    // TODO: think about if it's better to put public Player createPlayer(String username) --> everything just related to game be solved here
    public TransferGameObject ConvertGameIntoTransferObject(Game gameObject){
        System.out.println("in Game Service");
        TransferGameObject tgo = new TransferGameObject();
        System.out.println("1");
        tgo.whoseTurn = gameObject.getGameStatus().getUserTurn();
        System.out.println("2");

        tgo.pilesList = gameObject.getPileList();
        System.out.println("3");

        Map<String, List<Card>> dictionary = new HashMap<>();
        Card c = new Card(12);
        List<Card> lc = new ArrayList<>();
        lc.add(c);
        dictionary.put("Anna",  lc);
        tgo.playerCards = dictionary;
        System.out.println("4");

        tgo.gameRunning = gameObject.getGameStatus().getGameRunning();
        System.out.println("vor return im Game Service");

        return tgo;


    }

    public Map<String, List<Card>> getPlayerCardsDictionary(Game gameObject){
        System.out.println("In getPlayerCardsDictionary");
       List<Player> pl = gameObject.getListOfPlayers();

        Map<String, List<Card>> dictionary = new HashMap<>();

        for (int i=0; i< gameObject.getListOfPlayers().size(); i++){
            System.out.println("dict loop");
            dictionary.put(pl.get(i).getPlayerName(), pl.get(i).getHandCards().getHandCards());
            System.out.println("nach dict put");
        }

        System.out.println("Vor return im game Service");
        return dictionary;


    }



}
