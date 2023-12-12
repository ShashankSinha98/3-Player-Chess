package common;

import java.util.*;

public class Board {

    /** A map from board positions to the pieces at that position **/
    private Map<Position,Piece> board;

    public Board(){//(int time){
        board = new HashMap<Position,Piece>();
        try{
            // Blue, Green, Red
            for(Colour c: Colour.values()){
                board.put(Position.get(c,0,0),new Piece(PieceType.ROOK,c)); board.put(Position.get(c,0,7), new Piece(PieceType.ROOK,c));
                board.put(Position.get(c,0,1),new Piece(PieceType.KNIGHT,c)); board.put(Position.get(c,0,6), new Piece(PieceType.KNIGHT,c));
                board.put(Position.get(c,0,2),new Piece(PieceType.BISHOP,c)); board.put(Position.get(c,0,5), new Piece(PieceType.BISHOP,c));
                board.put(Position.get(c,0,3),new Piece(PieceType.QUEEN,c)); board.put(Position.get(c,0,4), new Piece(PieceType.KING,c));
                for(int i = 0; i<8; i++){
                    board.put(Position.get(c,1,i), new Piece(PieceType.PAWN,c));
                }
            }
        }catch(InvalidPositionException e){}//no impossible positions in this code
    }

    public void move(Position start, Position end) {
        Piece movingPiece = board.get(start);
        if(movingPiece != null)
            board.remove(start);

        board.put(end, movingPiece);
    }

    public Map<Position, Piece> getBoardMap() { return this.board; }

    public boolean isEmpty(int squareIndex) throws InvalidPositionException {
        return !board.containsKey(Position.get(squareIndex));
    }

}
