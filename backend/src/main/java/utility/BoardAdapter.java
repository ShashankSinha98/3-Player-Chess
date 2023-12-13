package utility;

import common.*;

import java.util.HashMap;
import java.util.Map;

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
    
}
