package main;

import abstraction.IGameInterface;
import common.Board;
import common.Colour;
import common.InvalidPositionException;
import common.Position;
import utility.BoardAdapter;
import utility.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameMain implements IGameInterface {

    private static final String TAG = GameMain.class.getSimpleName();

    private Board board;
    private boolean isGameRunning;
    // private Colour turn;

    private Position moveStartPos, moveEndPos;
    private List<Position> highlightSquares;


    public GameMain() {
        initGame();
        // play();
    }

    private void initGame() {
        Log.d(TAG, "initGame()");
        board = new Board();
        isGameRunning = false;
        // turn = Colour.BLUE;
        moveStartPos = moveEndPos = null;
        highlightSquares = new ArrayList<>();
    }

    public void play() {
        if(!isGameRunning) {

        }
    }


    @Override
    public Map<String, String> getBoard() {
        return board.getWebViewBoard();
    }

    @Override
    public Map<String, String> onClick(int squarePos) {
        Log.d(TAG, ">>> onClick called: "+squarePos);
        try {
            if (moveStartPos == null) {
                moveStartPos = Position.get(squarePos);
                highlightSquares = board.getPossibleMoves(moveStartPos);
                Log.d(TAG, ">>> moveStartPos: " + moveStartPos);
            } else {
                moveEndPos = Position.get(squarePos);
                board.move(moveStartPos, moveEndPos);
                Log.d(TAG, ">>> moveStartPos: " + moveStartPos + ", moveEndPos: " + moveEndPos);

                moveStartPos = moveEndPos = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception onClick: "+e.getMessage());
        }

        return getBoard();
    }

    @Override
    public Colour getTurn() {
        return board.getTurn();
    }

    @Override
    public List<String> getHighlightSquarePositions() {
        return BoardAdapter.convertHighlightSquaresToViewBoard(highlightSquares);
    }

    public static void main(String[] args) {

    }
}
