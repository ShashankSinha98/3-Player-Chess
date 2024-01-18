package model;

import common.Colour;

/**
 * Moves like a knight. It cannot take a piece, however others can take it out.
 * When it takes any piece of different colour, it switches its position with that piece. (except Wall)
 * */
public class Jester extends Knight {

    /**
     * Jester constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Jester(Colour colour) {
        super(colour);
    }

    /**
     * Returns custom string representation of the class
     * @return String
     * */
    @Override
    public String toString() {
        return this.colour.toString()+"J";
    }
}
