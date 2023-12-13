package common;

public class InvalidPositionException extends Exception{
    public InvalidPositionException(String msg){
        super("Invalid Position: "+msg);
    }
}