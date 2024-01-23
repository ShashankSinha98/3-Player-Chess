package model;

import common.Colour;
import common.Direction;
import common.InvalidPositionException;
import common.Position;
import utility.Log;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static utility.MovementUtil.step;
import static utility.MovementUtil.stepOrNull;

/**
 * Wall class extends Rook. The polygons
 * to be highlighted, and its legal moves are checked here
 * Move like a rook. It cannot take any piece and other pieces cannot take it too.
 * Pieces of different colours cannot pass it through, however, pieces of the same colour
 * as the wall can pass it through.
 **/
public class Wall extends Rook {

    private static final String TAG = "WALL";

    /**
     * Wall constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Wall(Colour colour) {
        super(colour);
    }

    /**
     *  To check whether a move is valid
     * @param board: Board class instance representing current game board
     * @param start: Start position of move
     * @param end: End position of move
     * @return True if a move is possible from start to end, else False
     * */
    @Override
    public boolean isLegalMove(Board board, Position start, Position end) {
        Map<Position, BasePiece> boardMap = board.boardMap;
        BasePiece mover = this;
        BasePiece target = boardMap.get(end);
        if(target != null) {
            return false; // Wall cannot take any piece
        }
        Colour moverCol = mover.getColour();
        if(target != null && moverCol == target.getColour()) {
            return false; // player cannot take its own piece
        }

        Direction[][] steps = this.directions;
        for (Direction[] step : steps) {
            try {
                Position tmp = step(mover, step, start);
                while (end != tmp && boardMap.get(tmp) == null) {
                    Log.d(TAG, "tmp: " + tmp);
                    tmp = step(mover, step, tmp, tmp.getColour() != start.getColour());
                }
                if (end == tmp) {
                    return true; // when end position is in range of rook and contains a piece
                }
            } catch (InvalidPositionException e) {
                Log.e(TAG, "InvalidPositionException: " + e.getMessage());
            }//do nothing, steps went off board.
        }
        return false;
    }

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param board: Board class instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to move
     * */
    @Override
    public Set<Position> getHighlightPolygons(Board board, Position start) {
        Map<Position, BasePiece> boardMap = board.boardMap;
        //List<Position> positions = new ArrayList<>();
        Set<Position> positionSet = new HashSet<>();
        BasePiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position tmp = stepOrNull(mover, step, start);
            while(tmp != null && boardMap.get(tmp)==null) {
                Log.d(TAG, "tmp: "+tmp);
                positionSet.add(tmp);
                tmp = stepOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
            }
        }

        return positionSet;
    }

    /**
     * Returns custom string representation of the class
     * @return String
     * */
    @Override
    public String toString() {
        return this.colour.toString()+"W";
    }
}
