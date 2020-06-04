package com.example.tictac;

public class Game {

    private String name;
    private String player1;
    private String player2;
    private int board = -1;
    private Boolean active = true;
    private Boolean turn = true;
    private int roundCount = 0;
    private Boolean players = true;
    private Boolean players2 = true;

    public Boolean getPlayers2() {return players2; }

    public void setPlayers2(Boolean players) { this.players2 = players;  }

    public Boolean getPlayers() {return players; }

    public void setPlayers(Boolean players) { this.players = players;  }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) { this.player1 = player1;  }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getBoard() {
        return board;
    }

    public void setBoard(int board) {
        this.board = board;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public Game(){

    }

    public String getObject() {
        return "Name: "+name+"\n host:"+player1;
    }
}
