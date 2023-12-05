package controller;

import java.util.*;

import model.common.*;
import model.utilities.ImpossiblePositionException;

/**
 * Main class for representing game state.
 * The board maps each position to the piece at that posiiton, 
 * or null is free. It also records previous moves, 
 * as well as whose move it is, and which pieces have been 
 * captured by which player.
 * **/
public class Board { // implements Cloneable, Serializable
	
	private static Board mBoardInstance = null;
	
	/** Serial version UID for Board serialization and storage**/
    // private static final long serialVersionUID = -8547775276050612530L;
    
	/** A map from board positions to the pieces at that position **/
    private HashMap<Position,Piece> board;
    
    /**A flag that is true if and only if a King has been captured**/
    private boolean gameOver = false;
    
    /**The player whose turn it is**/
    private Colour turn = Colour.BLUE;//Blue goes first
    
    /**The moves taken so far, represented as an array of two positions, the start and end of the move**/
    private ArrayList<Position[]> history;//can only be changed by taking moves
    
    /**A map indicating which player has taken which piece, to support alternative scoring methods**/
    private HashMap<Colour,ArrayList<Piece>> captured;
    ///**A Map representing the remaining time allowed for each player, in milliseconds**/
    //private HashMap<Colour,Integer> timeLeft;
	
	public static Board getInstance() {
		if(mBoardInstance == null) {
			mBoardInstance = new Board();
		}
		
		return mBoardInstance;
	}
	
	/**
	   * Initialises the board, placing all pieces at their initial position.
	   * Note, unlike two person chess, the Queen is always on the left, and the King is always on his own colour.
	   * @param time the number of milliseconds each player has in total for the entire game.
	   * **/
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
	    }catch(ImpossiblePositionException e){}//no impossible positions in this code
	    history = new ArrayList<Position[]>();
	    captured = new HashMap<Colour,ArrayList<Piece>>();
	    //timeLeft = new HashMap<Colour,Integer>();
	    for(Colour c: Colour.values()){
	      captured.put(c,new ArrayList<>());
	      //timeLeft.put(c,time);
	    }
	  }

}
