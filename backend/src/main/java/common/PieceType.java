package common;

public enum PieceType{

	PAWN, //P
	KNIGHT, //N
	BISHOP, //B
	ROOK, //R
	QUEEN, //Q
	KING; //K

    private static Direction[][] pawnSteps(){
        return new Direction[][] {{Direction.FORWARD},{Direction.FORWARD,Direction.FORWARD},
                {Direction.FORWARD,Direction.LEFT},{Direction.LEFT,Direction.FORWARD},{Direction.FORWARD,Direction.RIGHT},
                {Direction.RIGHT,Direction.FORWARD}};
    }

    public Direction[][] getSteps(){
        switch(this){
            case PAWN: return pawnSteps();
            default: return pawnSteps();
        }
    }

    @Override
    public String toString() {
        switch(this) {
            case PAWN: return "P";
            case KNIGHT: return "N";
            case BISHOP: return "B";
            case ROOK: return "R";
            case QUEEN: return "Q";
            default: return "K";
        }
    }
}