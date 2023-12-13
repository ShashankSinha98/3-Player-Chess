package common;

public enum PieceType{

	PAWN, //P
	KNIGHT, //N
	BISHOP, //B
	ROOK, //R
	QUEEN, //Q
	KING; //K

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