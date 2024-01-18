package model;

import common.Colour;

public class Jester extends Knight {

    public Jester(Colour colour) {
        super(colour);
    }

    @Override
    public String toString() {
        return this.colour.toString()+"J";
    }
}
