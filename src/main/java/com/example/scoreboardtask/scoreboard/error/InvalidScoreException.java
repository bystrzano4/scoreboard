package com.example.scoreboardtask.scoreboard.error;

public class InvalidScoreException extends RuntimeException {
    public InvalidScoreException(final String message) {
        super(message);
    }
}
