package common;

import utility.BoardAdapter;
import utility.Log;

import java.util.*;

public class Board {

    private static final String TAG = "Board";
    
    /** A map from board positions to the pieces at that position **/
    private Map<Position,Piece> board;
    private Colour turn;
    private boolean gameOver;
    private String winner;

    public Board(){
        board = new HashMap<Position,Piece>();
        turn = Colour.BLUE;
        gameOver = false;
        winner = null;
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

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinner() {
        return winner;
    }


    public void move(Position start, Position end) throws InvalidMoveException {
        if(isLegalMove(start, end)) {
            Piece mover = board.get(start);
            Piece taken = board.get(end);
            board.remove(start);//empty start square
            board.put(end,mover);//move piece

            if(taken !=null){
                if(taken.getType()==PieceType.KING) {
                    gameOver=true;
                    winner = mover.getColour().toString();
                }
            }
            turn = Colour.values()[(turn.ordinal()+1)%3];
        } else throw new InvalidMoveException("Illegal Move: "+start+"-"+end);
    }

    public boolean isLegalMove(Position start, Position end){
        Piece mover = getPiece(start);
        Piece target = getPiece(end);
        if(mover==null) return false; // No piece present at start pos
        Colour moverCol = mover.getColour();
        if(moverCol!=turn) return false; // piece colour mismatches player colour
        if(target!= null && moverCol==target.getColour())return false; // player cannot take i'ts own piece

        Direction[][] steps = mover.getType().getSteps();  // get possible direction of the piece clicked

        switch(mover.getType()){
            case PAWN://note, there is no two step first move
                for(int i = 0; i<steps.length; i++){
                    try{
                        if(end == step(mover,steps[i],start) &&
                                ((target==null && i==0) // 1 step forward, not taking
                                        || (target==null && i==1 // 2 steps forward,
                                        && start.getColour()==moverCol && start.getRow()==1 //must be in initial position
                                        && board.get(Position.get(moverCol,2,start.getColumn()))==null)//and can't jump a piece
                                        || (target!=null && i>1)//or taking diagonally
                                )
                        )
                            return true;
                    }catch(InvalidPositionException e){}//do nothing, steps went off board.
                }
                //castling: Must have king and rook in their original positions, although they may have moved
                try{
                    if(start==Position.get(moverCol,0,4)){
                        if(end==Position.get(moverCol,0,6)){
                            Piece castle = board.get(Position.get(moverCol,0,7));
                            Piece empty1 = board.get(Position.get(moverCol,0,5));
                            Piece empty2 = board.get(Position.get(moverCol,0,6));
                            if(castle!=null && castle.getType()==PieceType.ROOK && castle.getColour()==mover.getColour()
                                    && empty1==null && empty2==null)
                                return true;
                        }
                        if(end==Position.get(moverCol,0,2)){
                            Piece castle = board.get(Position.get(moverCol,0,0));
                            Piece empty1 = board.get(Position.get(moverCol,0,1));
                            Piece empty2 = board.get(Position.get(moverCol,0,2));
                            Piece empty3 = board.get(Position.get(moverCol,0,3));
                            if(castle!=null && castle.getType()==PieceType.ROOK && castle.getColour()==mover.getColour()
                                    && empty1==null && empty2==null && empty3==null)
                                return true;
                        }
                    }
                }catch(InvalidPositionException e){}//do nothing, all positions possible here.
                break;

            case KNIGHT:
            case KING://note, you can move into check or remain in check. You may also castle across check
                for(int i = 0; i<steps.length; i++){
                    try{
                        if(end == step(mover, steps[i],start))
                            return true;
                    }catch(InvalidPositionException e){}//do nothing, steps went off board.
                }
                break;

            default: //rook, bishop, queen, just need to check that one of their steps is iterated.
                for(int i = 0; i<steps.length; i++){
                    Direction[] step = steps[i];
                    try{
                        Position tmp = step(mover,step,start);
                        while(end != tmp && board.get(tmp)==null){
                            tmp = step(mover, step, tmp, tmp.getColour()!=start.getColour());
                        }
                        if(end==tmp) return true;
                    }catch(InvalidPositionException e){}//do nothing, steps went off board.
                }
                break;
        }
        return false;
    }

    public Position step(Piece piece, Direction[] step, Position current) throws InvalidPositionException {
        boolean reverse = false;
        for(Direction d: step){
            if((piece.getColour()!=current.getColour() && piece.getType() == PieceType.PAWN) || reverse){//reverse directions for knights
                switch(d){
                    case FORWARD: d = Direction.BACKWARD; break;
                    case BACKWARD: d = Direction.FORWARD; break;
                    case LEFT: d = Direction.RIGHT; break;
                    case RIGHT: d = Direction.LEFT; break;
                }
            }
            Position next = current.neighbour(d);
            if(next.getColour()!= current.getColour()){//need to reverse directions when switching between sections of the board
                reverse=true;
            }
            current = next;
        }
        return current;
    }

    public Position step(Piece piece, Direction[] step, Position current, boolean reverse) throws InvalidPositionException {
        for(Direction d: step){
            if((piece.getColour()!=current.getColour() && piece.getType() == PieceType.PAWN) || reverse){//reverse directions for knights
                switch(d){
                    case FORWARD: d = Direction.BACKWARD; break;
                    case BACKWARD: d = Direction.FORWARD; break;
                    case LEFT: d = Direction.RIGHT; break;
                    case RIGHT: d = Direction.LEFT; break;
                }
            }
            Position next = current.neighbour(d);
            if(next.getColour()!= current.getColour()){//need to reverse directions when switching between sections of the board
                reverse=true;
            }
            current = next;
        }
        return current;
    }


    public Colour getTurn(){
        return turn;
    }

    public Piece getPiece(Position position){
        return board.get(position);
    }

    public Map<String, String> getWebViewBoard() {
        return BoardAdapter.convertModelBoardToViewBoard(this.board);
    }

    public List<Position> getPossibleMoves(Position pos) {
        List<Position> possibleMoves = new ArrayList<>();
        Piece mover = board.get(pos);
        if(mover != null) {
            for(Position pi: Position.values()) {
                if(isLegalMove(pos, pi)) {
                    possibleMoves.add(pi);
                }
            }
        }
        Log.d(TAG, "getPossibleMoves: "+possibleMoves);
        return possibleMoves;
    }

    // returns true if there is a piece on current square which matches the colour of
    // player in turn
    public boolean isCurrentPlayersPiece(Position pos) {
        return getPiece(pos) != null && getPiece(pos).getColour()==turn;
    }

}
