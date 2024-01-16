package utility;

import abstraction.BasePiece;
import common.*;
import model.Pawn;
import utility.Log;

public class MovementUtil {

    private static final String TAG = "MovementUtil";

    public static Position step(BasePiece piece, Direction[] step, Position current) throws InvalidPositionException {
        boolean reverse = false;
        for(Direction d: step){
            if((piece.getColour()!=current.getColour() && piece instanceof Pawn) || reverse){//reverse directions for knights
                switch(d){
                    case FORWARD: d = Direction.BACKWARD; break;
                    case BACKWARD: d = Direction.FORWARD; break;
                    case LEFT: d = Direction.RIGHT; break;
                    case RIGHT: d = Direction.LEFT; break;
                }
            }
            Position next = current.neighbour(d);
            if(next.getColour()!= current.getColour()){//need to reverse directions when switching between sections of the board
                reverse=true;
            }
            current = next;
        }
        return current;
    }

    public static Position step(BasePiece piece, Direction[] step, Position current, boolean reverse) throws InvalidPositionException {
        for(Direction d: step){
            if((piece.getColour()!=current.getColour() && piece instanceof Pawn) || reverse){//reverse directions for knights
                switch(d){
                    case FORWARD: d = Direction.BACKWARD; break;
                    case BACKWARD: d = Direction.FORWARD; break;
                    case LEFT: d = Direction.RIGHT; break;
                    case RIGHT: d = Direction.LEFT; break;
                }
            }
            Position next = current.neighbour(d);
            if(next.getColour()!= current.getColour()){//need to reverse directions when switching between sections of the board
                reverse=true;
            }
            current = next;
        }
        return current;
    }


    public static Position stepOrNull(BasePiece piece, Direction[] step, Position current) {
        try {
            return step(piece, step, current);
        } catch (InvalidPositionException e) {
            Log.e(TAG, "Exception: "+e.getMessage());
            return null;
        }
    }

    public static Position stepOrNull(BasePiece piece, Direction[] step, Position current, boolean reverse) {
        try {
            return step(piece, step, current, reverse);
        } catch (InvalidPositionException e) {
            Log.e(TAG, "Exception: "+e.getMessage());
            return null;
        }
    }
}
