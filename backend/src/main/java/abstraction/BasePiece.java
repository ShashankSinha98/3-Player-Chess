package abstraction;

import common.*;
import model.Board;
import utility.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Abstract Base class for all chess pieces. All chess pieces must extend this class
 *  and provide implementation to abstract methods according to their rules.
 * */
public abstract class BasePiece {

    protected Colour colour; // colour of the chess piece [Red, Green, Blue]
    protected Direction[][] directions; // List of possible directions a piece can move. [Left, Right, Forward, Backward]

    /**
     * @param colour: Colour of the chess piece being initiated
     * */
    public BasePiece(Colour colour) {
        this.colour = colour;
        setupDirections();
    }

    // To init directions list of a particular piece
    protected abstract void setupDirections();

    /**
     *  To check whether a move is valid
     * @param board: Board class instance representing current game board
     * @param start: Start position of move
     * @param end: End position of move
     * @return True if a move is possible from start to end, else False
     * */
    public abstract boolean isLegalMove(Board board, Position start, Position end);


    /**
     * Fetch all the possible positions where a piece can move on board
     * @param board: Board class instance representing current game board
     * @param start: position of piece on board
     * @return List of possible positions a piece is allowed to move
     * */
    public abstract List<Position> getHighlightPolygons(Board board, Position start);

    /**
     * @return Colour of the chess piece
     * */
    public Colour getColour() {
        return this.colour;
    }
}
