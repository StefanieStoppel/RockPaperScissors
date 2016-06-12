package com.rockpaperscissors.model;

import java.util.Map;

public class Round {

    private static final String resultTemplate = "You played: %s \n The computer played: %s \n";
    private static final String winnerTemplate = "%s wins round %2d.";
    private static final String drawTemplate = "Round %2d is a draw.";
    private static final String errorTemplate = "Round %2d was invalid. %s";

    private final long count;
    private final Map<String, String> moves;
    private final int winnerId;
    private String output;

    public Round(long count, Map<String, String> moves, int winnerId) {
        this.count = count;
        this.moves = moves;
        this.winnerId = winnerId;
        setOutput(count, moves, winnerId);
    }

    public long getCount() {
        return count;
    }

    public Map<String, String> getMoves() {
        return moves;
    }

    public int getWinnerId() {
        return winnerId;
    }

    private void setOutput(long round, Map<String, String> moves, int winnerIdx) {
        // Add info about who played which object this round
        String output = String.format(resultTemplate, moves.get("Player"), moves.get("Computer"));
        // Append win / draw message to output
        if(winnerIdx != -1) {
            if(winnerIdx == Integer.MIN_VALUE) {
                output += String.format(errorTemplate, round, "No GameStrategy was set.");
            } else {
                String winner = moves.keySet().toArray()[winnerIdx].toString();
                output += String.format(winnerTemplate, winner, round);
            }
        } else {
            output += String.format(drawTemplate, round);
        }
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}