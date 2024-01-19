package model;

import common.Colour;
import common.InvalidMoveException;
import common.InvalidPositionException;
import common.Position;
import utility.BoardAdapter;
import utility.Log;

import java.util.*;

public class Board {

    private static final String TAG = "Board";
    
    /** A map from board positions to the pieces at that position **/
    protected Map<Position, BasePiece> boardMap;
    protected Map<BasePiece, Position> wallPieceMapping;
    private Colour turn;
    private boolean gameOver;
    private String winner;

    public Board(){
        boardMap = new HashMap<Position,BasePiece>();
        wallPieceMapping = new HashMap<>();
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
        boardMap.put(rookStartPositions[0], new Rook(c)); boardMap.put(rookStartPositions[1], new Rook(c));

        // place KNIGHT
        Position[] knightStartPositions = new Position[] {Position.get(c,0,1), Position.get(c,0,6)};
        boardMap.put(knightStartPositions[0], new Knight(c)); boardMap.put(knightStartPositions[1], new Knight(c));

        // place BISHOP
        Position[] bishopStartPositions = new Position[] {Position.get(c,0,2), Position.get(c,0,5)};
        boardMap.put(bishopStartPositions[0], new Bishop(c)); boardMap.put(bishopStartPositions[1], new Bishop(c));

        // place Queen
        Position queenStartingPosition = Position.get(c,0,3);
        boardMap.put(queenStartingPosition, new Queen(c));

        // place KING
        Position kingStartingPosition = Position.get(c,0,4);
        boardMap.put(kingStartingPosition, new King(c));

        // place PAWN
        for(int i = 1; i<7; i++){
            Position ithPawnPosition = Position.get(c,1,i);
            boardMap.put(ithPawnPosition, new Pawn(c));
        }

        // place JESTER
        Position jesterStartPosition = Position.get(c,1,0);
        boardMap.put(jesterStartPosition, new Jester(c));

        // place WALL
        Position wallStartPosition = Position.get(c, 1, 7);
        BasePiece wall = new Wall(c);
        boardMap.put(wallStartPosition, wall);
        wallPieceMapping.put(wall, wallStartPosition);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinner() {
        return winner;
    }


    public void move(Position start, Position end) throws InvalidMoveException, InvalidPositionException {
        if(isLegalMove(start, end)) {
            BasePiece mover = boardMap.get(start);
            BasePiece taken = boardMap.get(end);
            boardMap.remove(start);  //empty start square

            if(mover instanceof Pawn && end.getRow()==0 && end.getColour()!=mover.getColour())
                boardMap.put(end, new Queen(mover.getColour()));  //promote pawn
            else if (mover instanceof Jester){
                // switch places
                boardMap.put(end,mover);
                boardMap.put(start, taken);
                if(taken instanceof Wall) {
                    wallPieceMapping.put(taken, start);
                }
            } else {
                boardMap.put(end,mover);  //move piece
                if(mover instanceof Wall) {
                    wallPieceMapping.put(mover, end);
                }
            }

            if(mover instanceof King && start.getColumn()==4 && start.getRow()==0){
                if(end.getColumn()==2){//castle left, update rook
                    Position rookPos = Position.get(mover.getColour(),0,0);
                    boardMap.put(Position.get(mover.getColour(),0,3), boardMap.get(rookPos));
                    boardMap.remove(rookPos);
                }else if(end.getColumn()==6){//castle right, update rook
                    Position rookPos = Position.get(mover.getColour(),0,7);
                    boardMap.put(Position.get(mover.getColour(),0,5), boardMap.get(rookPos));
                    boardMap.remove(rookPos);
                }
            }

            if(taken !=null){
                if(taken instanceof King) {
                    gameOver=true;
                    winner = mover.getColour().toString();
                } else if(taken instanceof Wall && !(mover instanceof Jester)) {
                    wallPieceMapping.remove(taken);
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
        boolean isLegalMove = mover.isLegalMove(this, start, end);
        Log.d(TAG, "isLegalMove: "+isLegalMove);
        return isLegalMove;
    }

    public Colour getTurn(){
        return turn;
    }

    public BasePiece getPiece(Position position){
        return boardMap.get(position);
    }

    public Map<String, String> getWebViewBoard() {
        return BoardAdapter.convertModelBoardToViewBoard(this.boardMap);
    }

    public List<Position> getPossibleMoves(Position pos) {
        List<Position> possibleMoves = new ArrayList<>();
        BasePiece mover = boardMap.get(pos);
        possibleMoves = mover.getHighlightSquares(this, pos);
        Log.d(TAG, "getPossibleMoves: "+possibleMoves);
        return possibleMoves;
    }

    // returns true if there is a piece on current square which matches the colour of
    // player in turn
    public boolean isCurrentPlayersPiece(Position pos) {
        return getPiece(pos) != null && getPiece(pos).getColour()==turn;
    }

}
