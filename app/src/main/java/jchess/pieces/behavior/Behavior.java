package jchess.pieces.behavior;

import jchess.Colors;
import jchess.Square;
import jchess.pieces.King;
import jchess.pieces.Piece;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Behavior<T extends Piece> {
    protected Piece piece;

    public Behavior(Piece piece){
        this.piece = piece;
    }

    abstract public Set<Square> getSquaresInRange();

    public Set<Square> getLegalMoves()
    {
        King ourKing = (piece.getPlayer().getColor() == Colors.WHITE) ?
                piece.getChessboard().getKingWhite() : piece.getChessboard().getKingBlack();

        King oponentsKing = (piece.getPlayer().getColor() == Colors.WHITE) ?
                piece.getChessboard().getKingBlack() : piece.getChessboard().getKingWhite();

        return getSquaresInRange().stream()
                .filter(square -> canMove(piece, square, ourKing, oponentsKing))
                .collect(Collectors.toSet());
    }

    private boolean canMove(Piece piece, Square sq, King ourKing, King oponentsKing)
    {
        return ourKing.willBeSafeWhenMoveOtherPiece(piece.getSquare(), sq)
                && (null == sq.getPiece() || piece.getPlayer() != sq.getPiece().getPlayer())
                && sq.getPiece() != oponentsKing;
    }


}
