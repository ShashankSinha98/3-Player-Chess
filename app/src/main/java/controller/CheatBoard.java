package controller;

import model.common.Position;

/**
 * Main class for representing game state.
 * The board maps each position to the piece at that posiiton, 
 * or null is free. It also records previous moves, 
 * as well as whose move it is, and which pieces have been 
 * captured by which player.
 * CheatBoard overrides the isLegalMove function to always return true, so any Board position can be used.
 * **/
public class CheatBoard extends Board implements Cloneable{

  /**
   * Creates a standard board
   * **/
  public CheatBoard(){
    super();
  }

  /** 
   * All moves are deemed legal
   * @return true
   * **/
  public boolean isLegalMove(Position start, Position end){
    return true;
  }

  /** All moves are legal so pointless to display. **/
  public boolean displayLegalMoves() {
    return false;
  }
}
