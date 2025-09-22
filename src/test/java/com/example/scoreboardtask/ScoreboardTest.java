package com.example.scoreboardtask;

import com.example.scoreboardtask.scoreboard.Game;
import com.example.scoreboardtask.scoreboard.Scoreboard;
import com.example.scoreboardtask.scoreboard.error.DuplicatedTeamNameException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreboardTest {

    @Test
    void startGame_shouldInitializeGameSuccessfully() {
        // given
        final Scoreboard scoreboard = new Scoreboard();

        // when
        scoreboard.startGame("Mexico", "Poland");

        // then
        final List<Game> summary = scoreboard.getSummary();
        assertThat(summary.size()).isEqualTo(1);

        final Game game = summary.get(0);
        assertThat(game.getHomeTeam()).isEqualTo("Mexico");
        assertThat(game.getAwayTeam()).isEqualTo("Poland");
        assertThat(game.getHomeScore()).isEqualTo(0);
        assertThat(game.getAwayScore()).isEqualTo(0);
    }

    @Test
    void startGame_givenDuplicatedTeamNames_shouldThrowDuplicatedTeamNameException() {
        // given
        final Scoreboard scoreboard = new Scoreboard();

        // when
        scoreboard.startGame("Mexico", "Poland");

        // then
        final DuplicatedTeamNameException duplicatedTeamNameException = assertThrows(
            DuplicatedTeamNameException.class,
            () -> scoreboard.startGame("Poland", "Mexico")
        );
        final String message = duplicatedTeamNameException.getMessage();
        assertThat(message).isEqualTo("Duplicated team names: Poland, Mexico");
    }

    @Test
    void finishGame_shouldRemoveGameFromScoreboardSuccessfully() {
        // given
        final Scoreboard scoreboard = new Scoreboard();

        // when
        scoreboard.startGame("Mexico", "Poland");
        scoreboard.startGame("Albania", "Croatia");
        scoreboard.finishGame("Mexico", "Poland");

        // then
        final List<Game> summary = scoreboard.getSummary();
        assertThat(summary.size()).isEqualTo(1);
        assertTrue(summary.stream().noneMatch(game ->
            game.getHomeTeam().equals("Mexico") && game.getAwayTeam().equals("Poland")));
    }

    @Test
    void finishGame_givenNonExistingTeamName_shouldThrowException() {

    }

    @Test
    void updateScore_shouldUpdateScoreSuccessfully() {

    }

    @Test
    void updateScore_invalidScore_shouldThrowException() {

    }

    @Test
    void updateScore_givenNonExistingGameName_shouldThrowException() {

    }

    @Test
    void getSummary_shouldReturnGamesInCorrectOrder() {

    }
}
