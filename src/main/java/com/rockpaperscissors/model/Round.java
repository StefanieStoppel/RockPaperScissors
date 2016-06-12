package com.rockpaperscissors.model;

import java.util.Map;

public class Round {

    private final long count;
    private final Map<String, String> moves;
    private final String winner;
    private String output;

    public Round(long count, Map<String, String> moves, String winner, String output) {
        this.count = count;
        this.moves = moves;
        this.winner = winner;
        this.output = output;
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

    public String getOutput() {
        return output;
    }
}