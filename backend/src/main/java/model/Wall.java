package model;

import abstraction.BasePiece;
import common.Colour;
import common.Direction;
import common.InvalidPositionException;
import common.Position;
import utility.Log;
import utility.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static utility.MovementUtil.step;
import static utility.MovementUtil.stepOrNull;

public class Wall extends Rook {

    private static final String TAG = "WALL";

    public Wall(Colour colour) {
        super(colour);
    }


    @Override
    public boolean isLegalMove(Board board, Position start, Position end) {
        Map<Position, BasePiece> boardMap = board.boardMap;
        BasePiece mover = this;
        BasePiece target = boardMap.get(end);
        if(mover==null) return false; // No piece present at start pos
        if(target!=null) return false; // Wall cannot take any piece
        Colour moverCol = mover.getColour();
        if(target!= null && moverCol==target.getColour())return false; // player cannot take it's own piece

        Direction[][] steps = this.directions;
        for(int i = 0; i<steps.length; i++){
            Direction[] step = steps[i];
            try{
                Position tmp = step(mover,step,start);
                while(end != tmp && boardMap.get(tmp)==null){
                    Log.d(TAG, "tmp: "+tmp);
                    tmp = step(mover, step, tmp, tmp.getColour()!=start.getColour());
                }
                if(end==tmp) return true; // when end pos is in range of rook and contains a piece
            }catch(InvalidPositionException e){
                Log.d(TAG, "InvalidPositionException: "+e.getMessage());
            }//do nothing, steps went off board.
        }
        return false;
    }

    @Override
    public List<Position> getHighlightPolygons(Board board, Position start) {
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

            /*if(tmp!=null) {
                if(boardMap.get(tmp).getColour()!=mover.getColour()) {
                    Log.d(TAG, "Opponent tmp: " + tmp);
                    positionSet.add(tmp);
                } else {
                    Log.d(TAG, "Mine tmp: " + tmp);
                }
            }*/
        }

        return Util.toList(positionSet);
    }

    @Override
    public String toString() {
        return this.colour.toString()+"W";
    }
}
