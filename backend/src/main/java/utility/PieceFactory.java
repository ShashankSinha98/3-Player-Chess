package utility;
import common.Colour;
import model.BasePiece;
import model.Bishop;
import model.Jester;
import model.King;
import model.Knight;
import model.Pawn;
import model.Queen;
import model.Rook;
import model.Wall;

public class PieceFactory {
    public static BasePiece createPiece(String type, Colour colour) {
        switch (type.toLowerCase()) {
            case "bishop":
                return new Bishop(colour);
            case "queen":
                return new Queen(colour);
            case "king":
                return new King(colour);
            case "knight":
                return new Knight(colour);
            case "rook":
                return new Rook(colour);
            case "pawn":
                return new Pawn(colour);
            case "jester":
                return new Jester(colour);
            case "wall":
                return new Wall(colour);
            default:
                throw new IllegalArgumentException("Invalid chess piece type: " + type);
        }
    }
}
