package application.gamelogic;

import java.util.HashMap;
import java.util.Map;

public class Board {

    public static Map<Square, Piece> getInitialPosition() {
        Map<Square, Piece> chessboard = new HashMap<>();
        setupPieces(Color.R, chessboard);
        setupPieces(Color.B, chessboard);
        setupPieces(Color.G, chessboard);
        
        return chessboard;
    }
    
    private static void setupPieces(Color color, Map<Square, Piece> chessboard) {
        Piece rook1 = new Piece(PieceSymbol.R, color);
        Square h1 = new Square(color, "h1");

        Piece rook2 = new Piece(PieceSymbol.R, color);
        Square a1 = new Square(color, "a1");

        Piece knight1 = new Piece(PieceSymbol.N, color);
        Square g1 = new Square(color, "g1");

        Piece knight2 = new Piece(PieceSymbol.N, color);
        Square b1 = new Square(color, "b1");

        Piece bishop1 = new Piece(PieceSymbol.B, color);
        Square c1 = new Square(color, "c1");

        Piece bishop2 = new Piece(PieceSymbol.B, color);
        Square f1 = new Square(color, "f1");

        Piece king = new Piece(PieceSymbol.K, color);
        Square e1 = new Square(color, "e1");

        Piece queen = new Piece(PieceSymbol.Q, color);
        Square d1 = new Square(color, "d1");

        chessboard.put(a1, rook1);
        chessboard.put(b1, knight1);
        chessboard.put(c1, bishop1);
        chessboard.put(d1, queen);
        chessboard.put(e1, king);
        chessboard.put(f1, bishop2);
        chessboard.put(g1, knight2);
        chessboard.put(h1, rook2);

        for (char i = 'a'; i <= 'h'; i++) {
            Square square = new Square(color, i+ "2");
            Piece pawn = new Piece(PieceSymbol.P, color);
            chessboard.put(square, pawn);
        }
    }
}
