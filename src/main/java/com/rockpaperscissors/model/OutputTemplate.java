package com.rockpaperscissors.model;

public final class OutputTemplate {

    public static final String INFO = "You played: %s \n The computer played: %s \n";
    public static final String WINNER = "%s wins round %2d.";
    public static final String DRAW = "Round %2d is a draw.";
    public static final String ERROR_INVALID = "Round %2d was invalid. Reason: %s";
    public static final String ERROR_INVALID_CHOICE_PLAYER = "Player's choice was invalid.";
    public static final String ERROR_INVALID_CHOICE_COMPUTER = "Computer's choice was invalid.";
}
