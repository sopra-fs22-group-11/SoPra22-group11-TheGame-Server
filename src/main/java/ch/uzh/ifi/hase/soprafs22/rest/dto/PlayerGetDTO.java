package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.PlayerStatus;

public class PlayerGetDTO {

  private Long id;
  private String password;
  private String playername;
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

  public String getPlayername() {
    return playername;
  }

  public void setPlayername(String playername) {
    this.playername = playername;
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
