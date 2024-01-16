package abstraction;

import common.*;
import utility.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasePiece {

    private static final String TAG = "BasePiece";

    protected Colour colour;
    protected Direction[][] directions;

    public BasePiece(Colour colour) {
        this.colour = colour;
        setupDirections();
    }

    protected abstract void setupDirections();

    public abstract boolean isLegalMove(Map<Position, BasePiece> board, Position start, Position end);

    public abstract List<Position> getHighlightSquares(Map<Position, BasePiece> board, Position start);

    public abstract Colour getColour();
}
