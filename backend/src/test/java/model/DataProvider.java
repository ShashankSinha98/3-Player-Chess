package model;

import common.Colour;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataProvider {
    static Stream<BasePiece> pieceProvider() {
        List<BasePiece> pieces = new ArrayList<>();

        for (Colour colour : Colour.values()) {
            pieces.add(new Pawn(colour));
            pieces.add(new Rook(colour));
            pieces.add(new Knight(colour));
            pieces.add(new Bishop(colour));
            pieces.add(new Queen(colour));
            pieces.add(new King(colour));
            pieces.add(new Jester(colour));
//            pieces.add(new Wall(colour));
        }

        return pieces.stream();
    }
}
