package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private UserStatus status;

  @Column(nullable = true) // It is either your turn: true, not your turn: false, or if you are not in a game then: null
  private Boolean yourTurn;

  @Column(nullable = false)
  private int winningCount;

  @Column(nullable = false)
  private int gameCount;

  @Column
  private HandCards handCards;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getPassword() {return password;}

  public void setPassword(String password) {
    this.password = password;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }


  public Boolean getYourTurn(){return yourTurn;}

  public void setYourTurn(Boolean yourTurn){this.yourTurn = yourTurn;}


  public int getWinningCount(){return winningCount;}

  public void setWinningCount(int winningCount){this.winningCount = winningCount;}


  public int getGameCount(){return gameCount;}

  public void setGameCount(int gameCount){this.gameCount = gameCount;}

  public HandCards getHandCards(){return handCards;}

}
