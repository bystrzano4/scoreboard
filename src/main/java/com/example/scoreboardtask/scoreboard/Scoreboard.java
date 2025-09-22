package com.example.scoreboardtask.scoreboard;

import com.example.scoreboardtask.date.DateProvider;
import com.example.scoreboardtask.scoreboard.error.*;

import java.util.*;
import java.util.stream.Collectors;

public class Scoreboard {

    private final List<Game> games = new ArrayList<>();
    private final DateProvider dateProvider;

    public Scoreboard() {
        this.dateProvider = new DateProvider();
    }

    public Scoreboard(final DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    public void startGame(String homeTeam, String awayTeam) {
        validateTeamsUniqueness(homeTeam, awayTeam);
        final Game game = new Game(homeTeam, awayTeam, 0, 0, dateProvider.getLocalDateTime());
        games.add(game);
    }

    public void finishGame(String homeTeam, String awayTeam) {
        final Game game = findGameByHomeAndAwayTeam(homeTeam, awayTeam);
        games.remove(game);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validateScores(homeScore, awayScore);
        final Game game = findGameByHomeAndAwayTeam(homeTeam, awayTeam);
        games.remove(game);
        final Game updatedGame = new Game(homeTeam, awayTeam, homeScore, awayScore, dateProvider.getLocalDateTime());
        games.add(updatedGame);
    }

    public List<Game> getSummary() {
        return games.stream()
            .sorted(
                Comparator.comparingInt(
                        (Game g) -> g.getHomeScore() + g.getAwayScore()
                    ).reversed()
                    .thenComparing(Game::getLastUpdatedAt, Comparator.reverseOrder())
            )
            .collect(Collectors.toList());
    }

    private void validateTeamsUniqueness(final String homeTeam, final String awayTeam) {
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

    private Game findGameByHomeAndAwayTeam(final String homeTeam, final String awayTeam) {
        return games.stream()
            .filter(g -> g.getHomeTeam().equals(homeTeam) && g.getAwayTeam().equals(awayTeam))
            .findFirst()
            .orElseThrow(() -> new GameNotFoundException("Game not found: " + homeTeam + " vs " + awayTeam));
    }

    private void validateScores(final int homeScore, final int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new InvalidScoreException("Scores must be non-negative");
        }
    }

}
