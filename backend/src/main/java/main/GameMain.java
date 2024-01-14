package main;

import abstraction.IGameInterface;
import common.*;
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


    // squarePos must be in range [0, 95]
    @Override
    public OnClickResponse onClick(int squarePos) {
        Log.d(TAG, ">>> onClick called: "+squarePos);
        try {
            Position pos = Position.get(squarePos);
            if (moveStartPos == null || board.isCurrentPlayersPiece(pos)) {
                moveStartPos = pos;
                Log.d(TAG, ">>> moveStartPos: " + moveStartPos);
                highlightSquares = board.getPossibleMoves(moveStartPos);
                if(highlightSquares.size() == 0) { // Selected piece has no square to move, reset selection
                    moveStartPos = null;
                }
            } else {
                moveEndPos = Position.get(squarePos);
                board.move(moveStartPos, moveEndPos);
                Log.d(TAG, ">>> moveStartPos: " + moveStartPos + ", moveEndPos: " + moveEndPos);

                moveStartPos = moveEndPos = null;
                highlightSquares = null;
            }
        } catch (InvalidMoveException e) {
            Log.e(TAG, "InvalidMoveException onClick: "+e.getMessage());
            moveStartPos = moveEndPos = null;
            highlightSquares = null;
        } catch (InvalidPositionException e) {
            Log.e(TAG, "InvalidPositionException onClick: "+e.getMessage());
        }

        OnClickResponse clickResponse = new OnClickResponse(getBoard(), getHighlightSquarePositions());
        Log.d(TAG, "ClickResponse: "+clickResponse);
        return clickResponse;
    }

    @Override
    public Colour getTurn() {
        return board.getTurn();
    }

    private List<String> getHighlightSquarePositions() {
        return BoardAdapter.convertHighlightSquaresToViewBoard(highlightSquares);
    }

    public static void main(String[] args) {

    }
}
