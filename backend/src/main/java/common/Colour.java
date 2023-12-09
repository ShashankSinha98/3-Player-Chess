package common;

public enum Colour {

    BLUE, GREEN, RED;

    @Override
    public String toString() {
        switch (this) {
            case BLUE: return "B";
            case GREEN: return "G";
            default: return "R";
        }
    }
}