package common;

import java.util.List;
import java.util.Map;

/**
 * Class GameState to communicate with the webapp.
 * All the info regarding highlighted polygons, winner is
 * accessed with this method
 */
public class GameState {

    private final List<String> highlightedPolygons;
    private final Map<String, String> board;
    private boolean isGameOver;
    private Map.Entry<Colour, String> winner;

    /**
     * GameState constructor
     */
    public GameState(Map<String, String> board, List<String> highlightedPolygons) {
        this.board = board;
        this.highlightedPolygons = highlightedPolygons;
    }

    /**
     * Method returns list of polygons to be highlighted
     * @return List of Strings
     */
    public List<String> getHighlightedPolygons() {
        return highlightedPolygons;
    }

    /**
     * Method to share the board info to the web app
     * @return Map with board position and piece
     */
    public Map<String, String> getBoard() {
        return board;
    }

    /**
     * Method to share the winner info to the web app
     * @param winner is set
     */
    public void setGameOver(Map.Entry<Colour, String> winner) {
        this.isGameOver = true;
        this.winner = winner;
    }

    /**
     * method to check if the game is over.
     * @return boolean isGameOver
     **/
    public boolean isGameOver() {
        return this.isGameOver;
    }

    /**
     * method to get the winner.
     * @return String winner
     **/
    public Map.Entry<Colour, String> getWinner() {
        return this.winner;
    }

    /**
     * method to get the highlighted polygons and the board.
     * @return String
     **/
    @Override
    public String toString() {
        return "GameState{" +
                "highlightedPolygons=" + highlightedPolygons +
                ", board=" + board +
                '}';
    }
}
