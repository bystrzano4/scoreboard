package com.example.scoreboardtask.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private List<Game> games = new ArrayList<>();

    public void startGame(String homeTeam, String awayTeam) {
        final Game game = new Game(homeTeam, awayTeam, 0, 0);
        games.add(game);
    }

    public List<Game> getSummary() {
        return games;
    }
}
