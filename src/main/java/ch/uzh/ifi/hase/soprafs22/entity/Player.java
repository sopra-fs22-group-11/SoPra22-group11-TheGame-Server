package ch.uzh.ifi.hase.soprafs22.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PLAYER")
public class Player {

    @Column(nullable = true) // It is either your turn: true, not your turn: false, or if you are not in a game then: null
    private Boolean yourTurn;

    @Column
    private HandCards handCards;

    public Boolean getYourTurn(){return yourTurn;}
    public void setYourTurn(Boolean yourTurn){this.yourTurn = yourTurn;}

    public HandCards getHandCards(){return handCards;}
}
