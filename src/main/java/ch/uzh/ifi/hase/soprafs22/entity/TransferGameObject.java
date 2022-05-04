package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.List;
import java.util.Map;

public class TransferGameObject {
    // TODO add deck, how many cards left on deck
    public int noCardsOnDeck;
    public String whoseTurn; // TODO get from game
    public List<Pile> pilesList;
    public Map<String, List<Card>> playerCards;
    //public List<Player> playerList;
    public boolean gameRunning;
}
