package com.example.scoreboardtask.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Game {

    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
}
