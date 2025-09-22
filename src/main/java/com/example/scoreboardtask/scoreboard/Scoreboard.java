package com.example.scoreboardtask.scoreboard;

import com.example.scoreboardtask.scoreboard.error.DuplicatedTeamNameException;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private final List<Game> games = new ArrayList<>();

    public void startGame(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);
        final Game game = new Game(homeTeam, awayTeam, 0, 0);
        games.add(game);
    }

    public void finishGame(String homeTeam, String awayTeam) {
        games.removeIf(game -> game.getHomeTeam().equals(homeTeam) || game.getAwayTeam().equals(awayTeam));
    }

    public List<Game> getSummary() {
        return games;
    }

    private void validateTeams(final String homeTeam, final String awayTeam) {
        final List<String> duplicatedNames = new ArrayList<>();
        addDuplicatedTeam(homeTeam, duplicatedNames);
        addDuplicatedTeam(awayTeam, duplicatedNames);
        if (!duplicatedNames.isEmpty()) {
            throw new DuplicatedTeamNameException("Duplicated team names: " + String.join(", ", duplicatedNames));
        }
    }

    private void addDuplicatedTeam(final String homeTeam, final List<String> duplicatedNames) {
        if (games.stream().anyMatch(
            game -> game.getHomeTeam().equals(homeTeam) || game.getAwayTeam().equals(homeTeam))) {
            duplicatedNames.add(homeTeam);
        }
    }

}
