package common;

import java.util.List;
import java.util.Map;

public class OnClickResponse {

    private final List<String> highlightedSquares;
    private final Map<String, String> board;

    private boolean isGameOver = false;
    private String winner = null;

    public OnClickResponse(Map<String, String> board, List<String> highlightedSquares) {
        this.board = board;
        this.highlightedSquares = highlightedSquares;
    }

    public List<String> getHighlightedSquares() {
        return highlightedSquares;
    }

    public Map<String, String> getBoard() {
        return board;
    }

    public void setGameOver(String winner) {
        this.isGameOver = true;
        this.winner = winner;
    }

    public boolean getIsGameOver() {
        return this.isGameOver;
    }

    @Override
    public String toString() {
        return "OnClickResponse{" +
                "highlightedSquares=" + highlightedSquares +
                ", board=" + board +
                '}';
    }
}
