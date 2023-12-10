package application.gamelogic;

public class Piece {
    private PieceSymbol piece;
    private Color color;

    private String name; //"Pawn1" "Pawn2"
    public Piece(PieceSymbol piece, Color color) {
        this.piece = piece;
        this.color = color;
    }

    public PieceSymbol getPiece() {
        return piece;
    }

    public void setPiece(PieceSymbol piece) {
        this.piece = piece;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
