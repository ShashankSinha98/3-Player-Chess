package model;

import common.Colour;
import common.Direction;
import common.Position;
import utility.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     *  To check whether a move is valid. It contains some basic checks which is done
     *  for all chess pieces
     * @param boardMap: Board Map instance representing current game board
     * @param start: Start position of move
     * @param end: End position of move
     * @return True if a move is possible from start to end, else False
     * */
    public boolean isLegalMove(Map<Position, BasePiece> boardMap, Position start, Position end) {
        Collection<Position> wallPiecePositions = getWallPieceMapping(boardMap).values();
        if(wallPiecePositions.contains(end)) return false;

        BasePiece mover = boardMap.get(start);
        BasePiece target = boardMap.get(end);
        if(mover==null) return false; // No piece present at start pos
        Colour moverCol = mover.getColour();
        if(target!= null && moverCol==target.getColour()) return false; // player cannot take its own piece

        boolean canPieceMove = mover.canMove(boardMap, start, end);
        Log.d(TAG, "canPieceMove: "+canPieceMove);

        return canPieceMove;
    }

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
     *  To check whether a piece can make a move from start to end.
     *  It needs to be implemented by all chess pieces and check the move according to their logic
     * @param boardMap: Board Map instance representing current game board
     * @param start: Start position of move
     * @param end: End position of move
     * @return True if a move is possible from start to end, else False
     * */
    protected abstract boolean canMove(Map<Position, BasePiece> boardMap, Position start, Position end);

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map representing current game board
     * @param start: position of piece on board
     * @return List of possible positions a piece is allowed to move
     * */
    public abstract List<Position> getHighlightPolygons(Map<Position, BasePiece> boardMap, Position start);

    /**
     * @return Colour of the chess piece
     * */
    public Colour getColour() {
        return this.colour;
    }
}
