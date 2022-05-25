package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.List;
import java.util.Map;

public class TransferGameObject {
    public int noCardsOnDeck;
    public String whoseTurn;
    public List<Pile> pilesList;
    public Map<String, List<Card>> playerCards;
    public boolean gameRunning;
}
