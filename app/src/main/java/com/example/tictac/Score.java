package com.example.tictac;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Score {
    private String name;
    private String player1;
    private String player2;
    private int score1;
    private int score2;
    private Date time;

    public String getScore(){
        return "Name: "+getName()+"\nPlayer 1: "+getPlayer1()+"\nPlayer 2: "+getPlayer2()+"\nWynik "+Integer.toString(getScore1())+" : "+Integer.toString(getScore2());
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

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
