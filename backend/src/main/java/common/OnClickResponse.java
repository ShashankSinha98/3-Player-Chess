package common;

import java.util.List;
import java.util.Map;

public class OnClickResponse {

    private final List<String> highlightedPolygons;
    private final Map<String, String> board;

    private boolean isGameOver = false;
    private String winner = null;

    public OnClickResponse(Map<String, String> board, List<String> highlightedPolygons) {
        this.board = board;
        this.highlightedPolygons = highlightedPolygons;
    }

    public List<String> getHighlightedPolygons() {
        return highlightedPolygons;
    }

    public Map<String, String> getBoard() {
        return board;
    }

    public void setGameOver(String winner) {
        this.isGameOver = true;
        this.winner = winner;
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    public String getWinner() {
        return this.winner;
    }

    @Override
    public String toString() {
        return "OnClickResponse{" +
                "highlightedPolygons=" + highlightedPolygons +
                ", board=" + board +
                '}';
    }
}
