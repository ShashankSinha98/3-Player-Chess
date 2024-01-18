package model;

import abstraction.BasePiece;
import common.*;
import utility.Log;
import utility.Util;

import java.util.*;

import static utility.MovementUtil.step;
import static utility.MovementUtil.stepOrNull;

public class Queen extends BasePiece {

    private static final String TAG = "QUEEN";

    public Queen(Colour colour) {
        super(colour);
    }

    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.FORWARD,Direction.LEFT},{Direction.FORWARD,Direction.RIGHT},
                {Direction.LEFT,Direction.FORWARD},{Direction.RIGHT,Direction.FORWARD},{Direction.BACKWARD,Direction.LEFT},
                {Direction.BACKWARD,Direction.RIGHT},{Direction.LEFT,Direction.BACKWARD},{Direction.RIGHT,Direction.BACKWARD},
                {Direction.FORWARD},{Direction.BACKWARD},{Direction.LEFT},{Direction.RIGHT}};
    }

    @Override
    public boolean isLegalMove(Map<Position, BasePiece> board, Position start, Position end) {
        BasePiece mover = this;
        BasePiece target = board.get(end);
        if(mover==null) return false; // No piece present at start pos
        Colour moverCol = mover.getColour();
        // if(moverCol!=turn) return false; // piece colour mismatches player colour //TODO - colour-turn check need to be handled on Game Main Side
        if(target!= null && moverCol==target.getColour()) return false; // player cannot take it's own piece

        Direction[][] steps = this.directions;
        for(int i = 0; i<steps.length; i++){
            Direction[] step = steps[i];
            try{
                Position tmp = step(mover,step,start);
                while(end != tmp && board.get(tmp)==null){
                    tmp = step(mover, step, tmp, tmp.getColour()!=start.getColour());
                }
                if(end==tmp) return true;
            }catch(InvalidPositionException e){}//do nothing, steps went off board.
        }

        return false;
    }

    @Override
    public List<Position> getHighlightSquares(Map<Position, BasePiece> board, Position start) {
        //List<Position> positions = new ArrayList<>();
        Set<Position> positionSet = new HashSet<>();

        BasePiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position tmp = stepOrNull(mover, step, start);
            while(tmp != null && !positionSet.contains(tmp) && board.get(tmp)==null) {
                Log.d(TAG, "tmp: "+tmp);
                //positions.add(tmp);
                positionSet.add(tmp); // to prevent same position to add in list again
                tmp = stepOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
            }

            if(tmp!=null && board.get(tmp)!=null) {
                if(board.get(tmp).getColour()!=mover.getColour()) {
                    Log.d(TAG, "Opponent tmp: " + tmp);
                    //positions.add(tmp);
                    positionSet.add(tmp);
                } else {
                    Log.d(TAG, "Mine tmp: " + tmp);
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
        return this.colour.toString()+"Q";
    }
}
