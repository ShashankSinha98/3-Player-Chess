package common;

/**
 *  Enum class containing the colors of different players.
 *  Also has a method for String representation to use in web app
 **/
public enum Colour {

    BLUE, GREEN, RED;

    public Colour next() {
        switch (this) {
            case BLUE: return GREEN;
            case GREEN: return RED;
            default: return BLUE;
        }
    }
    @Override
    public String toString() {
        switch (this) {
            case BLUE: return "B";
            case GREEN: return "G";
            default: return "R";
        }
    }
}
