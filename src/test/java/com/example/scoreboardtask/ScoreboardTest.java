package com.example.scoreboardtask;

import com.example.scoreboardtask.scoreboard.Game;
import com.example.scoreboardtask.scoreboard.Scoreboard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    void startGame_givenDuplicatedTeamNames_shouldThrowException() {

    }

    @Test
    void finishGame_shouldRemoveGameFromScoreboardSuccessfully() {

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
