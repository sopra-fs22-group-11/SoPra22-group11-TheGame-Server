package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.HandCards;

import java.util.List;

public class PlayerGetDTO extends UserGetDTO{
    private Boolean yourTurn;
    private HandCards handCards;

    public Boolean getYourTurn(){return yourTurn;}
    public void setYourTurn(Boolean yourTurn){this.yourTurn = yourTurn;}

    public HandCards getHandCards(){return handCards;}
    public void setHandCards(HandCards handCards){this.handCards = handCards;}
}
