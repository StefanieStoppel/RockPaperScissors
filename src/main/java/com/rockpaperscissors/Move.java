package com.rockpaperscissors;

class Move {

    private final long round;
    private final String moveType;

    Move(long round, String moveType) {
        this.round = round;
        this.moveType = moveType;
    }

    public long getRound() {
        return round;
    }

    public String getMoveType() {
        return moveType;
    }
}