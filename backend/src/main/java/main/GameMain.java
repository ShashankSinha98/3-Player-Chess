package main;

import abstraction.IGameInterface;
import common.Colour;
import common.InvalidMoveException;
import common.InvalidPositionException;
import common.OnClickResponse;
import common.Position;
import model.Board;
import utility.BoardAdapter;
import utility.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utility.BoardAdapter.calculatePolygonId;

/**
 * Class containing the main logic of the backend.
 * the click inputs from the webapp is communicated with the backend.
 */
public class GameMain implements IGameInterface {

    private static final String TAG = GameMain.class.getSimpleName();
    private final Board board;
    private Position moveStartPos, moveEndPos;
    private List<Position> highlightPolygons;

    /**
     * GameMain Constructor. Entry point to the backend logic
     * */
    public GameMain() {
        Log.d(TAG, "initGame GameMain()");
        board = new Board();
        moveStartPos = null;
        moveEndPos = null;
        highlightPolygons = new ArrayList<>();
    }

    @Override
    public Map<String, String> getBoard() {
        return board.getWebViewBoard();
    }

    // polygonPos must be in range [0, 95]
    @Override
    public OnClickResponse onClick(String polygonLabel) {
        try {
            Log.d(TAG, ">>> onClick called: polygonLabel: "+polygonLabel);
            int polygonPos = calculatePolygonId(polygonLabel);
            Log.d(TAG, ">>> onClick called: polygonPos:  "+polygonPos);

            Position position = Position.get(polygonPos);
            if (board.isCurrentPlayersPiece(position)) { // player selects his own piece - first move
                moveStartPos = position;
                Log.d(TAG, ">>> moveStartPos: " + moveStartPos);
                highlightPolygons = board.getPossibleMoves(moveStartPos);
                if(highlightPolygons.isEmpty()) { // Selected piece has no polygon to move, reset selection
                    moveStartPos = null;
                }
            } else if(moveStartPos != null){
                moveEndPos = Position.get(polygonPos);
                board.move(moveStartPos, moveEndPos);
                Log.d(TAG, ">>> moveStartPos: " + moveStartPos + ", moveEndPos: " + moveEndPos);

                moveStartPos = moveEndPos = null;
                highlightPolygons = null;
            }
        } catch (InvalidMoveException e) {
            Log.e(TAG, "InvalidMoveException onClick: "+e.getMessage());
            moveStartPos = moveEndPos = null;
            highlightPolygons = null;
        } catch (InvalidPositionException e) {
            Log.e(TAG, "InvalidPositionException onClick: "+e.getMessage());
            moveStartPos = moveEndPos = null;
            highlightPolygons = null;
        }
        List<String> highlightPolygonsList = BoardAdapter.convertHighlightPolygonsToViewBoard(highlightPolygons);
        OnClickResponse clickResponse = new OnClickResponse(getBoard(), highlightPolygonsList);
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

    public static void main(String[] args) { }

}
