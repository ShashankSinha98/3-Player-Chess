import abstraction.IGameInterface;
import utility.BoardAdapter;
import common.*;

import java.util.Map;

public class GameMain implements IGameInterface {

    private static final String TAG = GameMain.class.getSimpleName();

    private Board mBoard;
    private boolean mIsGameRunning;
    private Colour mTurn;

    private Position mStartPos, mEndPos;
    private Integer[] mHighlightSquares;


    public GameMain() {
        initGame();
        // play();
    }

    private void initGame() {
        mBoard = new Board();
        mIsGameRunning = false;
        mTurn = Colour.BLUE;
        mStartPos = mEndPos = null;
        mHighlightSquares = null;
    }

    public void play() {
        if(!mIsGameRunning) {

        }
    }


    @Override
    public Map<String, String> getBoard() {
        return BoardAdapter.convertModelBoardToViewBoard(mBoard.getBoardMap());
    }

    @Override
    public Map<String, String> onClick(int squarePos) throws ImpossiblePositionException {
        if(mStartPos == null) {
            mStartPos = Position.get(squarePos);
            mHighlightSquares = new Integer[] {79, 51}; // hardcoded for now
        } else {
            mEndPos = Position.get(squarePos);
            mBoard.move(mStartPos, mEndPos);

            // reset start and end position
            mStartPos = mEndPos = null;
        }

        return getBoard();
    }

    @Override
    public Colour getTurn() {
        return mTurn;
    }

    @Override
    public Integer[] getHighlightSquarePositions() {
        return mHighlightSquares;
    }
}
