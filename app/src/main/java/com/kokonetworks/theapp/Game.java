package com.kokonetworks.theapp;

public class Game {
    public int score;
    public GameState state;
    public int level;

    public Game(int score, GameState state, int level) {
        this.score = score;
        this.state = state;
        this.level = level;
    }
}
