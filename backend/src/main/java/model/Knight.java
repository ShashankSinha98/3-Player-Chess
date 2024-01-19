package model;

import common.*;
import utility.Log;
import utility.Util;

import java.util.*;

import static utility.MovementUtil.step;
import static utility.MovementUtil.stepOrNull;

public class Knight extends BasePiece {

    private static final String TAG = "KNIGHT";

    public Knight(Colour colour) {
        super(colour);
    }

    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.FORWARD,Direction.FORWARD,Direction.LEFT},
                {Direction.FORWARD,Direction.FORWARD,Direction.RIGHT},{Direction.FORWARD,Direction.LEFT,Direction.LEFT},
                {Direction.FORWARD,Direction.RIGHT,Direction.RIGHT},{Direction.BACKWARD,Direction.BACKWARD,Direction.LEFT},
                {Direction.BACKWARD,Direction.BACKWARD,Direction.RIGHT},{Direction.BACKWARD,Direction.LEFT,Direction.LEFT},
                {Direction.BACKWARD,Direction.RIGHT,Direction.RIGHT},{Direction.LEFT,Direction.LEFT,Direction.FORWARD},
                {Direction.LEFT,Direction.LEFT,Direction.BACKWARD},{Direction.LEFT,Direction.FORWARD,Direction.FORWARD},
                {Direction.LEFT,Direction.BACKWARD,Direction.BACKWARD},{Direction.RIGHT,Direction.RIGHT,Direction.FORWARD},
                {Direction.RIGHT,Direction.RIGHT,Direction.BACKWARD},{Direction.RIGHT,Direction.FORWARD,Direction.FORWARD},
                {Direction.RIGHT,Direction.BACKWARD,Direction.BACKWARD}};
    }

    @Override
    public boolean canMove(Board board, Position start, Position end) {
        Map<Position, BasePiece> boardMap = board.boardMap;
        BasePiece mover = this;

        Direction[][] steps = this.directions;
        for(int i = 0; i<steps.length; i++){
            try{
                if(end == step(mover, steps[i],start))
                    return true;
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

        for(Direction[] step: steps) {
            Position end = stepOrNull(mover, step, start);

            if(positionSet.contains(end) || wallPiecePositions.contains(end)) continue;

            if(end != null) {
                BasePiece target = boardMap.get(end);

                if(target!=null) {
                    if(target.getColour()!=mover.getColour()) {
                        Log.d(TAG, "pos enemy: "+end);
                        positionSet.add(end);
                    }
                } else {
                    Log.d(TAG, "pos: "+end);
                    positionSet.add(end);
                }
            }
        }

        return Util.toList(positionSet);
    }

    @Override
    public String toString() {
        return this.colour.toString()+"N";
    }
}
