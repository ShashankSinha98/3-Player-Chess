package model;

import common.Colour;

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
