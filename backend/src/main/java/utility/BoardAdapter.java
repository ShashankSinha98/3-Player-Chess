package utility;

import abstraction.BasePiece;
import common.*;

import java.util.*;

public class BoardAdapter {

    public static Map<String, String> convertModelBoardToViewBoard(Map<Position, BasePiece> modelBoard) {
        Map<String, String> viewBoard = new HashMap<>();

        for(Position position: modelBoard.keySet()) {
            BasePiece piece = modelBoard.get(position);
            if(piece != null) {
                viewBoard.put(position.toString(), piece.toString());
            }
        }

        return  viewBoard;
    }

    public static List<String> convertHighlightPolygonsToViewBoard(List<Position> possibleMoves) {
        List<String> moves = new ArrayList<>();
        if(possibleMoves == null) return Collections.emptyList();
        for(Position pi: possibleMoves) {
            moves.add(pi.toString());
        }

        return moves;
    }
    
}
