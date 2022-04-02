package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;

public class PlayerGetDTO {

  private Long id;
  private String password; // Can maybe be removed if no one is using it
  private String username;
  private PlayerStatus status;
  private Boolean yourTurn;
  private int winningCount;
  private int gameCount;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public PlayerStatus getStatus() {
    return status;
  }

  public void setStatus(PlayerStatus status) {
    this.status = status;
  }

  public Boolean getYourTurn(){return yourTurn;}

    public void setYourTurn(Boolean yourTurn){this.yourTurn = yourTurn;}

    public int getWinningCount(){return winningCount;}

    public void setWinningCount(int winningCount){this.winningCount = winningCount;}


    public int getGameCount(){return gameCount;}

    public void setGameCount(int gameCount){this.gameCount = gameCount;}


}
