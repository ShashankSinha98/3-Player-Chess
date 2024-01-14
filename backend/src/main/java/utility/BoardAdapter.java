package utility;

import common.*;

import java.util.*;

public class BoardAdapter {

    public static Map<String, String> convertModelBoardToViewBoard(Map<Position, Piece> modelBoard) {
        Map<String, String> viewBoard = new HashMap<>();

        for(Position pos: modelBoard.keySet()) {
            Piece piece = modelBoard.get(pos);
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
