package model.common;

import controller.Board;

public abstract class Agent { // implements Runnable{

	  // private Board brd;
	  // private Position[] mv;
	  
	  /**
	   * 0 argument constructor. 
	   * This is the constructor that will be used to create the agent in tournaments.
	   * It may be (and probably should be) overidden in the implementing class.
	   * **/
	  public Agent(){}

	  /** Can be overridden to mark an Agent as requiring manual input for moves. **/
	  public boolean isAutonomous() {
	      return true;
	  }

	  /**
	   * Play a move in the game. 
	   * The agent is given a Board Object representing the position of all pieces, 
	   * the history of the game and whose turn it is. 
	   * They respond with a move represented by a pair (two element array) of positions: 
	   * the start and the end position of the move.
	   * @param board The representation of the game state.
	   * @return a two element array of Position objects, where the first element is the 
	   * current position of the piece to be moved, and the second element is the 
	   * position to move that piece to.
	   * **/
	  public abstract Position[] playMove(Board board);

	  /**
	   * @return the Agent's name, for annotating game description.
	   * **/ 
	  public abstract String toString();

	  /**
	   * Displays the final board position to the agent, 
	   * if required for learning purposes. 
	   * Other a default implementation may be given.
	   * @param finalBoard the end position of the board
	   * **/
	  public abstract void finalBoard(Board finalBoard);

	  /**
	   * For running threaded games.
	   * **/
//	  public final void setBoard(Board board){brd = board;}
//	  public final Position[] getMove(){return mv;}
//	  public void run(){
//	    mv = playMove(brd);
//	  }

	}


