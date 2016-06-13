package com.rockpaperscissors.model;

import com.rockpaperscissors.config.GameConfiguration;

import java.util.Map;

public class Round {

    private final long count;
    private final Map<String, String> moves;
    private final String winner;
    private String output;

    public Round(long count, Map<String, String> moves, String winner) {
        this.count = count;
        this.moves = moves;
        this.winner = winner;
        this.output = createOutput(count, moves.get(GameConfiguration.PLAYER),
                                    moves.get(GameConfiguration.COMPUTER));
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

    private String createOutput(long roundCount, String playersChoice, String computersChoice) {
        // Add Info about who played which hand
        String output = String.format(OutputTemplate.INFO, playersChoice, computersChoice);

        if(winner.equals(GameConfiguration.COMPUTER) || winner.equals(GameConfiguration.PLAYER)) {
            output += String.format(OutputTemplate.WINNER, winner, roundCount);
        } else if(winner.length() == 0) {
            output += String.format(OutputTemplate.DRAW, roundCount);
        } else {
            output += String.format(OutputTemplate.ERROR_INVALID, roundCount, winner);
        }
        return output;
    }
}