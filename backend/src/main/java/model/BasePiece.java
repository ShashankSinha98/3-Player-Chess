package model;

import common.Colour;
import common.Direction;
import common.Position;
import utility.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  Abstract Base class for all chess pieces. All chess pieces must extend this class
 *  and provide implementation to abstract methods according to their rules.
 **/
public abstract class BasePiece {

    private static final String TAG = "BasePiece";

    protected Colour colour; // colour of the chess piece [Red, Green, Blue]
    protected Direction[][] directions; // List of possible directions a piece can move. [Left, Right, Forward, Backward]

    /**
     * BasePiece constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public BasePiece(Colour colour) {
        this.colour = colour;
        setupDirections();
    }

    /**
     * Method to initialize directions for a chess piece
     **/
    protected abstract void setupDirections();

    protected Map<BasePiece, Position> getWallPieceMapping(Map<Position, BasePiece> boardMap) {
        Map<BasePiece, Position> res = new HashMap<>();
        for(Position pos: boardMap.keySet()) {
            BasePiece piece = boardMap.get(pos);
            if(piece instanceof Wall) {
                res.put(piece, pos);
            }
        }

        return res;
    }

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map representing current game board
     * @param start: position of piece on board
     * @return List of possible positions a piece is allowed to move
     * */
    public abstract Set<Position> getHighlightPolygons(Map<Position, BasePiece> boardMap, Position start);

    /**
     * @return Colour of the chess piece
     * */
    public Colour getColour() {
        return this.colour;
    }
}
