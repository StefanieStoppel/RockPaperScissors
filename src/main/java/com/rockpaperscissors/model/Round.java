package com.rockpaperscissors.model;

import java.util.Map;

public class Round {

    private final long count;
    private final Map<String, String> moves;
    private String winner = "";
    private String message = "";

    public Round(long count, Map<String, String> moves) {
        this.count = count;
        this.moves = moves;
    }

    public Round(long count, Map<String, String> moves, String winner) {
        this.count = count;
        this.moves = moves;
        this.winner = winner;
    }

    public long getCount() {
        return count;
    }

    public Map<String, String> getMoves() {
        return moves;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        if(winner == null) {
            throw new IllegalArgumentException("setWinner() called with argument null");
        }
        this.winner = winner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if(message == null) {
            throw new IllegalArgumentException("setMessage() called with argument null");
        }
        this.message = message;
    }
}