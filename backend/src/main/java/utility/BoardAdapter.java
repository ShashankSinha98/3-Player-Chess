package utility;

import abstraction.BasePiece;
import common.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Class BoardAdapter to convert information for web app representable form
 **/
public class BoardAdapter {

    /**
     *  Method to convert board data to Map of Strings for webapp
     * @param modelBoard a map of position and piece as input
     * @return Map of String and String
     **/
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

    /**
     *  Method to convert list of positions to highlight to list of strings
     * @param possibleMoves a list of positions to highlight
     * @return list of strings
     **/
    public static List<String> convertHighlightPolygonsToViewBoard(List<Position> possibleMoves) {
        List<String> moves = new ArrayList<>();
        if(possibleMoves == null) return Collections.emptyList();
        for(Position pi: possibleMoves) {
            moves.add(pi.toString());
        }

        return moves;
    }
    
}
