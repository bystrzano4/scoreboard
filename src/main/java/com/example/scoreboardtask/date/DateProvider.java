package com.example.scoreboardtask.date;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public class DateProvider {

    public LocalDateTime getLocalDateTime() {
        return java.time.LocalDateTime.now();
    }
}
