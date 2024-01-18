package model;

import abstraction.BasePiece;
import common.*;
import utility.Log;
import utility.Util;

import java.util.*;

import static utility.MovementUtil.step;
import static utility.MovementUtil.stepOrNull;

public class Bishop extends BasePiece {

    private static final String TAG = "BISHOP";

    public Bishop(Colour colour) {
        super(colour);
    }

    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.FORWARD,Direction.LEFT},{Direction.FORWARD,Direction.RIGHT},
                {Direction.LEFT,Direction.FORWARD},{Direction.RIGHT,Direction.FORWARD},{Direction.BACKWARD,Direction.LEFT},
                {Direction.BACKWARD,Direction.RIGHT},{Direction.LEFT,Direction.BACKWARD},{Direction.RIGHT,Direction.BACKWARD}};
    }

    @Override
    public boolean isLegalMove(Board board, Position start, Position end) {
        Map<Position, BasePiece> boardMap = board.boardMap;

        BasePiece mover = this;
        BasePiece target = boardMap.get(end);
        if(mover==null) return false; // No piece present at start pos
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

    @Override
    public List<Position> getHighlightSquares(Board board, Position start) {
        Map<Position, BasePiece> boardMap = board.boardMap;
        Collection<Position> wallPiecePositions = board.wallPieceMapping.values();

        Set<Position> positionSet = new HashSet<>();

        BasePiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position tmp = stepOrNull(mover, step, start);
            while(tmp != null && !positionSet.contains(tmp)
                    && (boardMap.get(tmp)==null || (boardMap.get(tmp) instanceof Wall && boardMap.get(tmp).getColour() == mover.getColour()))) {
                Log.d(TAG, "tmp: "+tmp);
                positionSet.add(tmp); // to prevent same position to add in list again
                tmp = stepOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
            }

            // found a piece diagonally
            if(tmp!=null && boardMap.get(tmp)!=null) {
                if(boardMap.get(tmp).getColour()!=mover.getColour()) {
                    Log.d(TAG, "Opponent tmp: " + tmp);
                    positionSet.add(tmp);
                } else {
                    Log.d(TAG, "Mine tmp: " + tmp);
                }
            }
        }

        for(Position pos: wallPiecePositions) {
            if(positionSet.contains(pos)) {
                Log.d(TAG, "Removed a wallPiecePos: "+pos);
                positionSet.remove(pos);
            }
        }

        return Util.toList(positionSet);
    }

    @Override
    public String toString() {
        return this.colour.toString()+"B";
    }
}
