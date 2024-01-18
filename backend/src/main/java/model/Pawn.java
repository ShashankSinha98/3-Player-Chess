package model;

import abstraction.BasePiece;
import common.*;
import utility.Log;
import utility.Util;

import java.util.*;

import static utility.MovementUtil.step;
import static utility.MovementUtil.stepOrNull;

public class Pawn extends BasePiece {

    private static final String TAG = "PAWN";

    public Pawn(Colour colour) {
        super(colour);
    }

    @Override
    public void setupDirections() {
        this.directions = new Direction[][] {{Direction.FORWARD},{Direction.FORWARD,Direction.FORWARD},
                {Direction.FORWARD,Direction.LEFT},{Direction.LEFT,Direction.FORWARD},{Direction.FORWARD,Direction.RIGHT},
                {Direction.RIGHT,Direction.FORWARD}};
    }

    @Override
    public boolean isLegalMove(Map<Position,BasePiece> board, Position start, Position end) {
        BasePiece mover = this;
        BasePiece target = board.get(end);
        if(mover==null) return false; // No piece present at start pos
        Colour moverCol = mover.getColour();
        // if(moverCol!=turn) return false; // piece colour mismatches player colour //TODO - colour-turn check need to be handled on Game Main Side
        if(target!= null && moverCol==target.getColour())return false; // player cannot take it's own piece

        Direction[][] steps = this.directions;
        for(int i = 0; i<steps.length; i++){
            try{
                if(end == step(mover,steps[i],start) && (
                                (target==null && i==0) // 1 step forward, not taking
                                || (target==null && i==1 // 2 steps forward,
                                    && start.getColour()==moverCol && start.getRow()==1 //must be in initial position
                                    && board.get(Position.get(moverCol,2,start.getColumn()))==null)//and can't jump a piece
                                || (target!=null && i>1)//or taking diagonally
                        )
                )
                    return true;
            }catch(InvalidPositionException e){}//do nothing, steps went off board.
        }
        return false;
    }


    @Override
    public List<Position> getHighlightSquares(Map<Position,BasePiece> board, Position start) {
        //List<Position> positions = new ArrayList<>();
        Set<Position> positionSet = new HashSet<>();
        BasePiece mover = this;
        Colour moverCol = mover.getColour();
        Direction[][] steps = this.directions;

        for (int i=0; i<steps.length; i++) {
            Direction[] step = steps[i];
            Position end = stepOrNull(mover, step, start);

            if(end!=null && !positionSet.contains(end)) {
                BasePiece target = board.get(end);
                Log.d(TAG, "end: "+end+", step: "+Arrays.toString(step));
                try {
                    if ((target == null && i == 0) // 1 step forward, not taking
                            || (target == null && i == 1 // 2 steps forward,
                            && start.getColour() == moverCol && start.getRow() == 1 //must be in initial position
                            && board.get(Position.get(moverCol, 2, start.getColumn())) == null)//and can't jump a piece
                            || (target != null && i > 1) //or taking diagonally
                    ) {
                        Log.d(TAG, "pos: " + end);
                        //positions.add(end);
                        positionSet.add(end);
                    }
                } catch (InvalidPositionException e) {
                    Log.d(TAG, "InvalidPositionException: "+e.getMessage());
                }
                }
            }

        return Util.toList(positionSet);
    }

    @Override
    public Colour getColour() {
        return this.colour;
    }

    @Override
    public String toString() {
        return this.colour.toString()+"P";
    }
}
