package jchess.pieces.behavior;

import jchess.Square;
import jchess.pieces.Bishop;
import jchess.pieces.King;
import jchess.pieces.Piece;

import java.util.Set;

public class BishopBehavior extends Behavior<Bishop>{
    public BishopBehavior(Piece piece) {
        super(piece);
    }

    @Override
    public Set<Square> getSquaresInRange() {
        return null;
    }
}
