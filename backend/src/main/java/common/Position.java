package common;

public enum Position{
 
  BA1(Colour.BLUE,0,0), BA2(Colour.BLUE,1,0), BA3(Colour.BLUE,2,0), BA4(Colour.BLUE,3,0),
  BB1(Colour.BLUE,0,1), BB2(Colour.BLUE,1,1), BB3(Colour.BLUE,2,1), BB4(Colour.BLUE,3,1),
  BC1(Colour.BLUE,0,2), BC2(Colour.BLUE,1,2), BC3(Colour.BLUE,2,2), BC4(Colour.BLUE,3,2),
  BD1(Colour.BLUE,0,3), BD2(Colour.BLUE,1,3), BD3(Colour.BLUE,2,3), BD4(Colour.BLUE,3,3),
  BE1(Colour.BLUE,0,4), BE2(Colour.BLUE,1,4), BE3(Colour.BLUE,2,4), BE4(Colour.BLUE,3,4),
  BF1(Colour.BLUE,0,5), BF2(Colour.BLUE,1,5), BF3(Colour.BLUE,2,5), BF4(Colour.BLUE,3,5),
  BG1(Colour.BLUE,0,6), BG2(Colour.BLUE,1,6), BG3(Colour.BLUE,2,6), BG4(Colour.BLUE,3,6),
  BH1(Colour.BLUE,0,7), BH2(Colour.BLUE,1,7), BH3(Colour.BLUE,2,7), BH4(Colour.BLUE,3,7),

  GA1(Colour.GREEN,0,0), GA2(Colour.GREEN,1,0), GA3(Colour.GREEN,2,0), GA4(Colour.GREEN,3,0),
  GB1(Colour.GREEN,0,1), GB2(Colour.GREEN,1,1), GB3(Colour.GREEN,2,1), GB4(Colour.GREEN,3,1),
  GC1(Colour.GREEN,0,2), GC2(Colour.GREEN,1,2), GC3(Colour.GREEN,2,2), GC4(Colour.GREEN,3,2),
  GD1(Colour.GREEN,0,3), GD2(Colour.GREEN,1,3), GD3(Colour.GREEN,2,3), GD4(Colour.GREEN,3,3),
  GE1(Colour.GREEN,0,4), GE2(Colour.GREEN,1,4), GE3(Colour.GREEN,2,4), GE4(Colour.GREEN,3,4),
  GF1(Colour.GREEN,0,5), GF2(Colour.GREEN,1,5), GF3(Colour.GREEN,2,5), GF4(Colour.GREEN,3,5),
  GG1(Colour.GREEN,0,6), GG2(Colour.GREEN,1,6), GG3(Colour.GREEN,2,6), GG4(Colour.GREEN,3,6),
  GH1(Colour.GREEN,0,7), GH2(Colour.GREEN,1,7), GH3(Colour.GREEN,2,7), GH4(Colour.GREEN,3,7),

  RA1(Colour.RED,0,0), RA2(Colour.RED,1,0), RA3(Colour.RED,2,0), RA4(Colour.RED,3,0),
  RB1(Colour.RED,0,1), RB2(Colour.RED,1,1), RB3(Colour.RED,2,1), RB4(Colour.RED,3,1),
  RC1(Colour.RED,0,2), RC2(Colour.RED,1,2), RC3(Colour.RED,2,2), RC4(Colour.RED,3,2),
  RD1(Colour.RED,0,3), RD2(Colour.RED,1,3), RD3(Colour.RED,2,3), RD4(Colour.RED,3,3),
  RE1(Colour.RED,0,4), RE2(Colour.RED,1,4), RE3(Colour.RED,2,4), RE4(Colour.RED,3,4),
  RF1(Colour.RED,0,5), RF2(Colour.RED,1,5), RF3(Colour.RED,2,5), RF4(Colour.RED,3,5),
  RG1(Colour.RED,0,6), RG2(Colour.RED,1,6), RG3(Colour.RED,2,6), RG4(Colour.RED,3,6),
  RH1(Colour.RED,0,7), RH2(Colour.RED,1,7), RH3(Colour.RED,2,7), RH4(Colour.RED,3,7);

  private final Colour colour; //red blue green
  private final int row; //0-3
  private final int column; //0-7

  private Position(Colour colour, int row, int column){
    this.colour = colour; this.row = row; this.column = column;
  }

  public Colour getColour(){return colour;}

  public int getRow(){return row;}

  public int getColumn(){return column;}

  @Override
  public String toString() {
    return colour.toString()+getColumnChar(column)+(row+1);
  }

  /**
   * Gets the position corresponding to the specified colour, row and column.
   * @return the position of the specified colour, row and column
   * @throws InvalidPositionException if outside the bounds of the board.
   * **/
  public static Position get(Colour colour, int row, int column) throws InvalidPositionException {
    int index= row+4*column;
    if(index>=0 && index<32){
      switch(colour){
        case BLUE: return Position.values()[index];
        case GREEN: return Position.values()[index+32];
        case RED: return Position.values()[index+64];
      }
    }
    throw new InvalidPositionException("No such position.");
  }

  /**
   * Gets the position corresponding to the specified colour, row and column.
   * @return the position of the specified square Index
   * @throws InvalidPositionException if outside the bounds of the board.
   * **/
  public static Position get(int squareIndex) throws InvalidPositionException {
      if(squareIndex >=0 && squareIndex <=95) {
        return Position.values()[squareIndex];
      }
    throw new InvalidPositionException("No such position.");
  }

  public Position neighbour(Direction direction) throws InvalidPositionException {
    switch(direction){
      case FORWARD:
        if(row<3) return get(colour, row+1, column);
        if(column<4) return get(Colour.values()[(colour.ordinal()+1)%3], 3, 7-column);
        return get(Colour.values()[(colour.ordinal()+2)%3],3,7-column);
      case BACKWARD:
        if(row==0) throw new InvalidPositionException("Moved off board");
        return get(colour,row-1,column);
      case LEFT:
        if(column==0) throw new InvalidPositionException("Moved off board");
        return get(colour,row,column-1);
      case RIGHT:
        if(column==7) throw new InvalidPositionException("Moved off board");
        return get(colour,row,column+1);
    }
    throw new InvalidPositionException("Unreachable code?");
  }

  public int getValue() {
    return this.row + (4 * this.column);
  }

  private char getColumnChar(int col) {
    switch (col) {
        case 0: return 'a';
        case 1: return 'b';
        case 2: return 'c';
        case 3: return 'd';
        case 4: return 'e';
        case 5: return 'f';
        case 6: return 'g';
        case 7: return 'h';
        default: return '\0';
    }
  }
}


