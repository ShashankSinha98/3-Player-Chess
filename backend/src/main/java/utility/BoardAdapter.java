package utility;

import model.BasePiece;
import common.*;

import java.util.*;

public class BoardAdapter {

    public static Map<String, String> convertModelBoardToViewBoard(Map<Position, BasePiece> modelBoard) {
        Map<String, String> viewBoard = new HashMap<>();

        for(Position pos: modelBoard.keySet()) {
            BasePiece piece = modelBoard.get(pos);
            if(piece != null) {
                viewBoard.put(pos.toString(), piece.toString());
            }
        }

        return  viewBoard;
    }

    public static List<String> convertHighlightSquaresToViewBoard(List<Position> possibleMoves) {
        List<String> moves = new ArrayList<>();
        if(possibleMoves == null) return Collections.emptyList();
        for(Position pi: possibleMoves) {
            moves.add(pi.toString());
        }

        return moves;
    }
    
}
