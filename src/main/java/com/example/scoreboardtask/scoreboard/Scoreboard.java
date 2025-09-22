package com.example.scoreboardtask.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private List<Game> games = new ArrayList<>();

    public void startGame(String homeTeam, String awayTeam) {

    }

    public List<Game> getSummary() {
        return games;
    }
}
