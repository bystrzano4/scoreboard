package com.example.scoreboardtask.scoreboard.error;

public class DuplicatedTeamNameException extends RuntimeException {
    public DuplicatedTeamNameException(String message) {
        super(message);
    }
}
