package model;

import common.Colour;
import common.Direction;
import common.InvalidPositionException;
import common.Position;
import utility.Log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static utility.MovementUtil.step;
import static utility.MovementUtil.stepOrNull;

/**
 * Rook class extends BasePiece. Move directions for the Rook, the polygons
 * to be highlighted, and its legal moves are checked here
 **/
public class Rook extends BasePiece {

    private static final String TAG = "ROOK";

    /**
     * Rook constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Rook(Colour colour) {
        super(colour);
        setupDirections();
    }

    /**
     * Method to initialize directions for a chess piece
     **/
    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.BACKWARD},{Direction.LEFT},{Direction.RIGHT},{Direction.FORWARD}};
    }


    /**
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to move
     * */
    @Override
    public Set<Position> getHighlightPolygons(Map<Position, BasePiece> boardMap, Position start) {
        Collection<Position> wallPiecePositions = getWallPieceMapping(boardMap).values();
        //List<Position> positions = new ArrayList<>();
        Set<Position> positionSet = new HashSet<>();
        BasePiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position tmp = stepOrNull(mover, step, start);
            while(tmp != null &&
                    (boardMap.get(tmp)==null || (boardMap.get(tmp) instanceof Wall && boardMap.get(tmp).getColour() == mover.getColour()))) {
                Log.d(TAG, "tmp: "+tmp);
                positionSet.add(tmp);
                tmp = stepOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
            }

            if(tmp!=null) {
                if(boardMap.get(tmp).getColour()!=mover.getColour()) {
                    Log.d(TAG, "Opponent tmp: " + tmp);
                    positionSet.add(tmp);
                } else {
                    Log.d(TAG, "Mine tmp: " + tmp);
                }
            }
        }

        for(Position position: wallPiecePositions) {
            if(positionSet.contains(position)) {
                Log.d(TAG, "Removed a wallPiecePos: "+position);
                positionSet.remove(position);
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
        return this.colour.toString()+"R";
    }
}
