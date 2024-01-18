package model;

import abstraction.BasePiece;
import common.Colour;
import common.Direction;
import common.InvalidPositionException;
import common.Position;
import utility.Log;
import utility.Util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static utility.MovementUtil.step;
import static utility.MovementUtil.stepOrNull;

/**
 * Queen class extends BasePiece. Move directions for the Queen, the polygons
 * to be highlighted, and its legal moves are checked here
 **/
public class Queen extends BasePiece {

    private static final String TAG = "QUEEN";

    /**
     * Queen constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Queen(Colour colour) {
        super(colour);
    }

    /**
     * Method to initialize directions for a chess piece
     **/
    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.FORWARD,Direction.LEFT},{Direction.FORWARD,Direction.RIGHT},
                {Direction.LEFT,Direction.FORWARD},{Direction.RIGHT,Direction.FORWARD},{Direction.BACKWARD,Direction.LEFT},
                {Direction.BACKWARD,Direction.RIGHT},{Direction.LEFT,Direction.BACKWARD},{Direction.RIGHT,Direction.BACKWARD},
                {Direction.FORWARD},{Direction.BACKWARD},{Direction.LEFT},{Direction.RIGHT}};
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
        if(mover==null) return false; // No piece present at start position
        Colour moverCol = mover.getColour();
        if(target!= null && moverCol==target.getColour()) return false; // player cannot take it's own piece

        Collection<Position> wallPiecePositions = board.wallPieceMapping.values();
        if(wallPiecePositions.contains(end)) return false;

        Direction[][] steps = this.directions;
        for(int i = 0; i<steps.length; i++){
            Direction[] step = steps[i];
            try{
                Position tmp = step(mover,step,start);
                while(end != tmp &&
                        (boardMap.get(tmp)==null)|| (boardMap.get(tmp) instanceof Wall && boardMap.get(tmp).getColour() == mover.getColour())){
                    tmp = step(mover, step, tmp, tmp.getColour()!=start.getColour());
                }
                if(end==tmp) return true;
            }catch(InvalidPositionException e){}//do nothing, steps went off board.
        }

        return false;
    }

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param board: Board class instance representing current game board
     * @param start: position of piece on board
     * @return List of possible positions a piece is allowed to move
     * */
    @Override
    public List<Position> getHighlightPolygons(Board board, Position start) {
        Map<Position, BasePiece> boardMap = board.boardMap;
        Collection<Position> wallPiecePositions = board.wallPieceMapping.values();

        Set<Position> positionSet = new HashSet<>();

        BasePiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position tmp = stepOrNull(mover, step, start);
            while(tmp != null && !positionSet.contains(tmp) &&
                    (boardMap.get(tmp)==null || (boardMap.get(tmp) instanceof Wall && boardMap.get(tmp).getColour() == mover.getColour()))) {
                Log.d(TAG, "tmp: "+tmp);
                //positions.add(tmp);
                positionSet.add(tmp); // to prevent same position to add in list again
                tmp = stepOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
            }

            if(tmp!=null && boardMap.get(tmp)!=null) {
                if(boardMap.get(tmp).getColour()!=mover.getColour()) {
                    Log.d(TAG, "Opponent tmp: " + tmp);
                    //positions.add(tmp);
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

        return Util.toList(positionSet);
    }

    /**
     * Returns custom string representation of the class
     * @return String
     * */
    @Override
    public String toString() {
        return this.colour.toString()+"Q";
    }
}
