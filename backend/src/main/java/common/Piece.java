package common;

public class Piece { 
    
  private final PieceType type; // the piece's type
  private final Colour colour; //the pieces colour

  /**
   * Constructs a piece of the given type and colour.
   * @param type the type of the piece
   * @param colour the colour of the piece
   * **/ 
  public Piece(PieceType type, Colour colour){
    this.type = type; this.colour = colour;
  }

  /**@return the type of the piece**/
  public PieceType getType(){return type;}
  
  /**@return the colour of the piece**/
  public Colour getColour(){return colour;}

  /**@return a String representation of the piece**/
  public String toString(){
    return colour.toString()+type.toString();
  }
}