package gamelogic;

import java.util.Map;

public interface GameInterface {
    public Map<String, String> move(String start, String end);

    //public Map<String, String> allMoves(String square);
}
