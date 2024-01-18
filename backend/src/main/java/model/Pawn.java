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
    public boolean isLegalMove(Board board, Position start, Position end) {
        Map<Position, BasePiece> boardMap = board.boardMap;
        BasePiece mover = this;
        BasePiece target = boardMap.get(end);
        if(mover==null) return false; // No piece present at start pos
        Colour moverCol = mover.getColour();
        if(target!= null && moverCol==target.getColour())return false; // player cannot take it's own piece

        Collection<Position> wallPiecePositions = board.wallPieceMapping.values();
        if(wallPiecePositions.contains(end)) return false;

        Direction[][] steps = this.directions;
        for(int i = 0; i<steps.length; i++){
            try{
                if(end == step(mover,steps[i],start) && (
                                (target==null && i==0) // 1 step forward, not taking
                                || (target==null && i==1 // 2 steps forward,
                                    && start.getColour()==moverCol && start.getRow()==1 //must be in initial position
                                    && boardMap.get(Position.get(moverCol,2,start.getColumn()))==null)//and can't jump a piece
                                || (target!=null && i>1)//or taking diagonally
                        )
                )
                    return true;
            }catch(InvalidPositionException e){}//do nothing, steps went off board.
        }
        return false;
    }


    @Override
    public List<Position> getHighlightSquares(Board board, Position start) {
        Map<Position, BasePiece> boardMap = board.boardMap;
        Collection<Position> wallPiecePositions = board.wallPieceMapping.values();
        //List<Position> positions = new ArrayList<>();
        Set<Position> positionSet = new HashSet<>();
        BasePiece mover = this;
        Colour moverCol = mover.getColour();
        Direction[][] steps = this.directions;

        for (int i=0; i<steps.length; i++) {
            Direction[] step = steps[i];
            Position end = stepOrNull(mover, step, start);

            if(wallPiecePositions.contains(end)) continue;

            if(end!=null && !positionSet.contains(end)) {
                BasePiece target = boardMap.get(end);
                Log.d(TAG, "end: "+end+", step: "+Arrays.toString(step));
                try {
                    if ((target == null && i == 0) // 1 step forward, not taking
                            || (target == null && i == 1 // 2 steps forward,
                            && start.getColour() == moverCol && start.getRow() == 1 //must be in initial position
                            && boardMap.get(Position.get(moverCol, 2, start.getColumn())) == null)//and can't jump a piece
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
    public String toString() {
        return this.colour.toString()+"P";
    }
}
