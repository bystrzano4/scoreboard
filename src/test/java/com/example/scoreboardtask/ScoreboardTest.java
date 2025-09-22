package com.example.scoreboardtask;

import com.example.scoreboardtask.date.DateProvider;
import com.example.scoreboardtask.scoreboard.Game;
import com.example.scoreboardtask.scoreboard.Scoreboard;
import com.example.scoreboardtask.scoreboard.error.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
        scoreboard.startGame("Mexico", "Poland");

        // when & then
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
        // given
        final Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame("Mexico", "Poland");
        scoreboard.startGame("Albania", "Croatia");

        // when & then
        final GameNotFoundException gameNotFoundException = assertThrows(
            GameNotFoundException.class,
            () -> scoreboard.finishGame("Brazil", "Poland")
        );
        final String message = gameNotFoundException.getMessage();
        assertThat(message).isEqualTo("Game not found: Brazil vs Poland");
    }

    @Test
    void updateScore_shouldUpdateScoreSuccessfully() {
        // given
        final Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame("Mexico", "Poland");

        // when
        scoreboard.updateScore("Mexico", "Poland", 1, 0);

        // then
        final List<Game> summary = scoreboard.getSummary();
        assertThat(summary.size()).isEqualTo(1);
        final Game game = summary.get(0);
        assertThat(game.getHomeTeam()).isEqualTo("Mexico");
        assertThat(game.getAwayTeam()).isEqualTo("Poland");
        assertThat(game.getHomeScore()).isEqualTo(1);
        assertThat(game.getAwayScore()).isEqualTo(0);
    }

    @Test
    void updateScore_invalidScore_shouldThrowException() {
        // given
        final Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame("Mexico", "Poland");

        // when & then
        assertThrows(InvalidScoreException.class, () -> scoreboard.updateScore("Mexico", "Poland", 1, -1));
    }

    @Test
    void updateScore_givenNonExistingTeamName_shouldThrowGameNotFoundException() {
        // given
        final Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame("Mexico", "Poland");

        // when & then
        assertThrows(GameNotFoundException.class, () -> scoreboard.updateScore("Argentina", "Poland", 1, 1));
    }

    @Test
    void getSummary_shouldReturnGamesInCorrectOrder() {
        // given
        final DateProvider dateProvider = Mockito.mock(DateProvider.class);
        mockDateTime(dateProvider);
        final Scoreboard scoreboard = new Scoreboard(dateProvider);
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.startGame("Spain", "Brazil");
        scoreboard.startGame("Germany", "France");
        scoreboard.startGame("Belarus", "USA");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Belarus", "USA", 6, 6);

        // when
        final List<Game> summary = scoreboard.getSummary();

        // then
        assertThat(summary.size()).isEqualTo(4);
        assertThat(summary.get(0).getHomeTeam()).isEqualTo("Belarus");
        assertThat(summary.get(0).getAwayTeam()).isEqualTo("USA");
        assertThat(summary.get(1).getHomeTeam()).isEqualTo("Spain");
        assertThat(summary.get(1).getAwayTeam()).isEqualTo("Brazil");
        assertThat(summary.get(2).getHomeTeam()).isEqualTo("Mexico");
        assertThat(summary.get(2).getAwayTeam()).isEqualTo("Canada");
        assertThat(summary.get(3).getHomeTeam()).isEqualTo("Germany");
        assertThat(summary.get(3).getAwayTeam()).isEqualTo("France");
    }

    private static void mockDateTime(final DateProvider dateProvider) {
        final LocalDateTime localDateTime = LocalDateTime.of(2025, 1, 1, 12, 0);
        when(dateProvider.getLocalDateTime())
            .thenReturn(localDateTime)
            .thenReturn(localDateTime.plusHours(1))
            .thenReturn(localDateTime.plusHours(2))
            .thenReturn(localDateTime.plusHours(3))
            .thenReturn(localDateTime.plusHours(4))
            .thenReturn(localDateTime.plusHours(5))
            .thenReturn(localDateTime.plusHours(6));
    }
}
