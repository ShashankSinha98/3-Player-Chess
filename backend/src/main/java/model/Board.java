package model;

import common.Colour;
import common.InvalidMoveException;
import common.InvalidPositionException;
import common.Position;
import utility.BoardAdapter;
import utility.Log;

import java.security.cert.TrustAnchor;
import java.util.*;

/**
 * Class containing the Board logic. To initialize the board with the pieces.
 * To highlight the possible moves, check for legal moves and move pieces.
 * Also, logic to declare winner.
 */
public class Board {

    private static final String TAG = "Board";
    
    /** A map from board positions to the pieces at that position **/
    protected Map<Position, BasePiece> boardMap;
    protected Map<BasePiece, Position> wallPieceMapping;
    private Colour turn;
    private boolean gameOver;
    private String winner;

    /**
     * Board constructor. Places pieces on the board and initializes variables
     * */
    public Board(){
        boardMap = new HashMap<Position,BasePiece>();
        wallPieceMapping = new HashMap<>();
        turn = Colour.BLUE;
        gameOver = false;
        winner = null;
        try{
            // Blue, Green, Red
            for(Colour colour: Colour.values()) {
                placeChessPieces(colour);
            }
        } catch(InvalidPositionException e) {
            Log.e(TAG, "InvalidPositionException: "+e.getMessage());
        }
    }

    /**
     * Place all the pieces on the board initially at start positions
     * @param colour for each color place the pieces
     * */
    private void placeChessPieces(Colour colour) throws InvalidPositionException {
        // place ROOK
        Position[] rookStartPositions = new Position[] {Position.get(colour,0,0), Position.get(colour,0,7)};
        boardMap.put(rookStartPositions[0], new Rook(colour)); boardMap.put(rookStartPositions[1], new Rook(colour));

        // place KNIGHT
        Position[] knightStartPositions = new Position[] {Position.get(colour,0,1), Position.get(colour,0,6)};
        boardMap.put(knightStartPositions[0], new Knight(colour)); boardMap.put(knightStartPositions[1], new Knight(colour));

        // place BISHOP
        Position[] bishopStartPositions = new Position[] {Position.get(colour,0,2), Position.get(colour,0,5)};
        boardMap.put(bishopStartPositions[0], new Bishop(colour)); boardMap.put(bishopStartPositions[1], new Bishop(colour));

        // place Queen
        Position queenStartingPosition = Position.get(colour,0,3);
        boardMap.put(queenStartingPosition, new Queen(colour));

        // place KING
        Position kingStartingPosition = Position.get(colour,0,4);
        boardMap.put(kingStartingPosition, new King(colour));

        // place PAWN
        for(int i = 1; i<7; i++){
            Position ithPawnPosition = Position.get(colour,1,i);
            boardMap.put(ithPawnPosition, new Pawn(colour));
        }

        // place JESTER
        Position jesterStartPosition = Position.get(colour,1,0);
        boardMap.put(jesterStartPosition, new Jester(colour));

        // place WALL
        Position wallStartPosition = Position.get(colour, 1, 7);
        BasePiece wall = new Wall(colour);
        boardMap.put(wallStartPosition, wall);
        wallPieceMapping.put(wall, wallStartPosition);
    }

     /**
     * To check if the game is over
     * @return boolean
     * */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * To fetch the winner
     * @return String of Winner name
     * */
    public String getWinner() {
        return winner;
    }

    /**
     * Called to move a piece from one position to another
     * @param start The start position
     * @param end The end position
     * */
    public void move(Position start, Position end) throws InvalidMoveException, InvalidPositionException {
        if(isLegalMove(start, end)) {
            BasePiece mover = boardMap.get(start);
            BasePiece taken = boardMap.get(end);
            boardMap.remove(start);  //empty start polygon

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

            for(Colour c: Colour.values()) {
                if(c!=turn) {
                    if(isCheckMate(c, boardMap)) {
                        gameOver = true;
                        winner = mover.getColour().toString();
                    }
                }
            }

            turn = Colour.values()[(turn.ordinal()+1)%3];
        } else throw new InvalidMoveException("Illegal Move: "+start+"-"+end);
    }

    /**
     * Checks if the piece can move from start to end positions
     * @param start The start position
     * @param end The end position
     * @return boolean
     * */
    public boolean isLegalMove(Position start, Position end){
        BasePiece mover = getPiece(start);
        BasePiece target = getPiece(end);
        if(mover==null) return false; // No piece present at start position
        Colour moverCol = mover.getColour();
        if(moverCol!=turn) return false; // piece colour mismatches player colour
        if(target!= null && moverCol==target.getColour())return false; // player cannot take i'ts own piece
        boolean isLegalMove = mover.isLegalMove(this.boardMap, start, end);
        Log.d(TAG, "isLegalMove: "+isLegalMove);


        if(isLegalMove) {
            if(isCheck(turn, boardMap) && isCheckAfterLegalMove(turn, boardMap, start, end)) {
                Log.d(TAG, "Colour "+moverCol+" is in check, this move doesn't help. Do again!!");
                return false;
            } else if(isCheckAfterLegalMove(turn, boardMap, start, end)) {
                Log.d(TAG, "Colour "+moverCol+" will be in check after this move");
                return false;
            } else{
                return true;
            }
        }

        return isLegalMove;
    }

    /**
     * Get the current player turn
     * @return Colour
     * */
    public Colour getTurn(){
        return turn;
    }

    /**
     * Get the piece on the selected position
     * @param position The current selected position
     * @return BasePiece
     * */
    private BasePiece getPiece(Position position){
        return boardMap.get(position);
    }

    /**
     * For the web app to use, board map is converted to string and returned
     * @return map of position and piece converted to strings
     * */
    public Map<String, String> getWebViewBoard() {
        return BoardAdapter.convertModelBoardToViewBoard(this.boardMap);
    }

    /**
     * For the current selected piece, returns the possible moves
     * @param position The current selected piece position
     * @return list of possible movements
     * */
    public List<Position> getPossibleMoves(Position position) {
        BasePiece mover = boardMap.get(position);
        if(mover == null) return new ArrayList<>();
        List<Position> possibleMoves = mover.getHighlightPolygons(this.boardMap, position);
        return possibleMoves;
    }

    /**
     * Tells if the current player has selected his own piece
     * @param position The current position of the piece
     * @return boolean
     * */
    public boolean isCurrentPlayersPiece(Position position) {
        return getPiece(position) != null && getPiece(position).getColour()==turn;
    }


    /**     Check / Check-mate logic helper functions **/

    private boolean isCheck(Colour colour, Map<Position, BasePiece> boardMap) {
        Position kingPosition = getKingPosition(colour, boardMap);

        for(Position position: boardMap.keySet()) {
            BasePiece piece = boardMap.get(position);
            if(piece.getColour()!=colour) {
                List<Position> possibleTargetPositions = piece.getHighlightPolygons(boardMap, position);
                if(possibleTargetPositions.contains(kingPosition)) {
                    Log.d(TAG, "Piece "+piece+" is attacking King of colour "+colour);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCheckMate(Colour colour, Map<Position, BasePiece> boardMap) {
        if(!isCheck(colour, boardMap)) {
            return false;
        }

        for(Position position: boardMap.keySet()) {
            BasePiece piece = boardMap.get(position);
            if(piece.getColour()==colour) {
                List<Position> possibleMoves = piece.getHighlightPolygons(boardMap, position);
                for(Position endPos: possibleMoves) {
                    if(!isCheckAfterLegalMove(colour, boardMap, position, endPos)) {
                        Log.d(TAG, "Piece "+piece+" can help colour "+colour+" to come out of check: st: "+position+", end: "+endPos);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isCheckAfterLegalMove(Colour colour, Map<Position, BasePiece> boardMap, Position start, Position end) {
        Map<Position, BasePiece> copyBoardMap = new HashMap<>(boardMap);
        BasePiece piece = copyBoardMap.get(start);
        copyBoardMap.remove(start);
        copyBoardMap.put(end, piece);

        if(!isCheck(colour, copyBoardMap)) {
            return false;
        }

        return true;
    }

    private Position getKingPosition(Colour colour, Map<Position, BasePiece> boardMap) {
        for(Position position: boardMap.keySet()) {
            BasePiece piece = boardMap.get(position);
            if(piece instanceof King && piece.getColour()==colour) return position;
        }
        return null;
    }
}
