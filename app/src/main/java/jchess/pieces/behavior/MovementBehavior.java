package jchess.pieces.behavior;

import jchess.Square;
import jchess.pieces.Piece;

import java.util.Set;

public interface MovementBehavior {

    Set<Square> getAllMoves();

}
