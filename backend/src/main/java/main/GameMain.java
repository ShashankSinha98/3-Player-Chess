package main;

import abstraction.*;
import common.*;
import model.Board;
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
    public OnClickResponse onClick(String squareLabel) {
        int squarePos = calculateSquareId(squareLabel);
        Log.d(TAG, ">>> onClick called: "+squarePos);
        try {
            Position pos = Position.get(squarePos);
            if (board.isCurrentPlayersPiece(pos)) { // player selects his own piece - first move
                moveStartPos = pos;
                Log.d(TAG, ">>> moveStartPos: " + moveStartPos);
                highlightSquares = board.getPossibleMoves(moveStartPos);
                if(highlightSquares.size() == 0) { // Selected piece has no square to move, reset selection
                    moveStartPos = null;
                }
            } else if(moveStartPos != null){
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
        if(board.isGameOver()) {
            String winner = board.getWinner();
            Log.d(TAG, "Winner: "+winner);
            clickResponse.setGameOver(winner);
        }
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

    public static void main(String[] args) { }

    private int calculateSquareId(String square){
        char color = square.charAt(0);
        int y = square.charAt(1) - 'a';
        int x = square.charAt(2) - '1';
        int offset = 0;
        if(color == 'G'){
            offset = 32;
        }else if(color == 'R'){
            offset = 64;
        }

        return offset + x + 4*y;
    }
}
