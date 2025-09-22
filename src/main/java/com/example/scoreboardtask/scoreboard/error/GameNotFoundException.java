package com.example.scoreboardtask.scoreboard.error;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
