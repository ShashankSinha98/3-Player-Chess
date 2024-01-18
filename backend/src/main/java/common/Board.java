package common;

import abstraction.*;
import model.*;
import utility.BoardAdapter;
import utility.Log;

import java.util.*;

public class Board {

    private static final String TAG = "Board";
    
    /** A map from board positions to the pieces at that position **/
    //public Map<Position,Piece> board;
    public Map<Position, BasePiece> board;
    private Colour turn;
    private boolean gameOver;
    private String winner;

    public Board(){
        //board = new HashMap<Position,Piece>();
        board = new HashMap<Position,BasePiece>();
        turn = Colour.BLUE;
        gameOver = false;
        winner = null;
        try{
            // Blue, Green, Red
            for(Colour c: Colour.values()){
                placeChessPieces(c);
            }
        }catch(InvalidPositionException e){}//no impossible positions in this code
    }

    private void placeChessPieces(Colour c) throws InvalidPositionException {
        // place ROOK
        Position[] rookStartPositions = new Position[] {Position.get(c,0,0), Position.get(c,0,7)};
        board.put(rookStartPositions[0], new Rook(c)); board.put(rookStartPositions[1], new Rook(c));

        // place KNIGHT
        Position[] knightStartPositions = new Position[] {Position.get(c,0,1), Position.get(c,0,6)};
        board.put(knightStartPositions[0], new Knight(c)); board.put(knightStartPositions[1], new Knight(c));

        // place BISHOP
        Position[] bishopStartPositions = new Position[] {Position.get(c,0,2), Position.get(c,0,5)};
        board.put(bishopStartPositions[0], new Bishop(c)); board.put(bishopStartPositions[1], new Bishop(c));

        // place Queen
        Position queenStartingPosition = Position.get(c,0,3);
        board.put(queenStartingPosition, new Queen(c));

        // place KING
        Position kingStartingPosition = Position.get(c,0,4);
        board.put(kingStartingPosition, new King(c));

        // place PAWN
        for(int i = 0; i<8; i++){
            Position ithPawnPosition = Position.get(c,1,i);
            board.put(ithPawnPosition, new Pawn(c));
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinner() {
        return winner;
    }


    public void move(Position start, Position end) throws InvalidMoveException, InvalidPositionException {
        if(isLegalMove(start, end)) {
            BasePiece mover = board.get(start);
            BasePiece taken = board.get(end);
            board.remove(start);  //empty start square

            if(mover instanceof Pawn && end.getRow()==0 && end.getColour()!=mover.getColour())
                board.put(end, new Queen(mover.getColour()));  //promote pawn
            else {
                board.put(end,mover);  //move piece
            }

            if(mover instanceof King && start.getColumn()==4 && start.getRow()==0){
                if(end.getColumn()==2){//castle left, update rook
                    Position rookPos = Position.get(mover.getColour(),0,0);
                    board.put(Position.get(mover.getColour(),0,3),board.get(rookPos));
                    board.remove(rookPos);
                }else if(end.getColumn()==6){//castle right, update rook
                    Position rookPos = Position.get(mover.getColour(),0,7);
                    board.put(Position.get(mover.getColour(),0,5),board.get(rookPos));
                    board.remove(rookPos);
                }
            }

            if(taken !=null){
                if(taken instanceof King) {
                    gameOver=true;
                    winner = mover.getColour().toString();
                }
            }
            turn = Colour.values()[(turn.ordinal()+1)%3];
        } else throw new InvalidMoveException("Illegal Move: "+start+"-"+end);
    }

    public boolean isLegalMove(Position start, Position end){
        BasePiece mover = getPiece(start);
        BasePiece target = getPiece(end);
        if(mover==null) return false; // No piece present at start pos
        Colour moverCol = mover.getColour();
        if(moverCol!=turn) return false; // piece colour mismatches player colour
        if(target!= null && moverCol==target.getColour())return false; // player cannot take i'ts own piece

        return mover.isLegalMove(board,start, end);
    }

    public Colour getTurn(){
        return turn;
    }

    public BasePiece getPiece(Position position){
        return board.get(position);
    }

    public Map<String, String> getWebViewBoard() {
        return BoardAdapter.convertModelBoardToViewBoard(this.board);
    }

    public List<Position> getPossibleMoves(Position pos) {
        List<Position> possibleMoves = new ArrayList<>();
        BasePiece mover = board.get(pos);
        possibleMoves = mover.getHighlightSquares(board, pos);
        Log.d(TAG, "getPossibleMoves: "+possibleMoves);
        return possibleMoves;
    }

    // returns true if there is a piece on current square which matches the colour of
    // player in turn
    public boolean isCurrentPlayersPiece(Position pos) {
        return getPiece(pos) != null && getPiece(pos).getColour()==turn;
    }

}
