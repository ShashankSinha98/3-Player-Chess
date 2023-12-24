package main;

import abstraction.IGameInterface;
import utility.BoardAdapter;
import common.*;
import utility.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameMain implements IGameInterface {

    private static final String TAG = GameMain.class.getSimpleName();

    private Board board;
    private boolean isGameRunning;
    private Colour turn;

    private Position moveStartPos, moveEndPos;
    private List<String> highlightSquares;


    public GameMain() {
        initGame();
        // play();
    }

    private void initGame() {
        Log.d(TAG, "initGame()");
        board = new Board();
        isGameRunning = false;
        turn = Colour.BLUE;
        moveStartPos = moveEndPos = null;
        highlightSquares = new ArrayList<>();
    }

    public void play() {
        if(!isGameRunning) {

        }
    }


    @Override
    public Map<String, String> getBoard() {
        return BoardAdapter.convertModelBoardToViewBoard(board.getBoardMap());
    }

    @Override
    public Map<String, String> onClick(int squarePos) throws InvalidPositionException {
        Log.d(TAG, "onClick called: "+squarePos);
        if(moveStartPos == null) {
            if(!board.isEmpty(squarePos) && board.getBoardMap().get(Position.get(squarePos)).getColour() == turn) {
                moveStartPos = Position.get(squarePos);
                Log.d(TAG, "First click, mStartPos: " + moveStartPos);
                highlightSquares.add("Ra4");
                highlightSquares.add("Bd3");
                highlightSquares.add("Gc2"); // hardcoded for now
            }  else {
                Log.d(TAG, "empty square or wrong player piece selected");
            }
        } else {
            moveEndPos = Position.get(squarePos);
            Log.d(TAG, "Second click, mStartPos: "+ moveStartPos +", mEndPos: "+ moveEndPos);
            board.move(moveStartPos, moveEndPos);

            // reset start and end position
            moveStartPos = moveEndPos = null;
            highlightSquares.clear();
            turn = Colour.values()[(turn.ordinal()+1)%3];
            Log.d(TAG, "turn: "+turn);
        }

        return getBoard();
    }

    @Override
    public Colour getTurn() {
        return turn;
    }

    @Override
    public List<String> getHighlightSquarePositions() {
        return highlightSquares;
    }
}
