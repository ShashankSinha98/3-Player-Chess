package application.gamelogic;

public class Square {
    private Color color;
    private String square;

    public Square(Color color, String square) {
        this.color = color;
        this.square = square;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    @Override
    public String toString() {
        return color.toString() + square;
    }

    @Override
    public boolean equals(Object obj) {
        return this.color == ((Square) obj).color && this.square.equals(((Square) obj).square);
    }

    @Override
    public int hashCode() {
        return square.hashCode();
    }
}
