package common;

public class InvalidMoveException extends Exception{
    public InvalidMoveException(String msg){
        super("Invalid Move: "+msg);
    }
}