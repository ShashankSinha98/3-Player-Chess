package main;

import abstraction.IGameInterface;
import com.google.common.collect.ImmutableSet;
import common.Colour;
import common.InvalidMoveException;
import common.InvalidPositionException;
import common.GameState;
import common.Position;
import model.Board;
import utility.BoardAdapter;
import utility.Log;
import utility.Settings;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static utility.BoardAdapter.calculatePolygonId;

/**
 * Class containing the main logic of the backend.
 * the click inputs from the webapp is communicated with the backend.
 */
public class GameMain implements IGameInterface {

    private static final String TAG = GameMain.class.getSimpleName();
    private final Board board;
    private Position moveStartPos, moveEndPos;
    private Set<Position> highlightPolygons;
    private Settings settings;

    /**
     * GameMain Constructor. Entry point to the backend logic
     * */
    public GameMain() {
        Log.d(TAG, "initGame GameMain()");
        board = new Board();
        moveStartPos = null;
        moveEndPos = null;
        highlightPolygons = ImmutableSet.of();
    }

    /**
     * Get the current board map being used by backend for current game session
     * @return Board map
     * */
    @Override
    public Map<String, String> getBoard() {
        return board.getWebViewBoard();
    }

    /**
     * Responsible for sending mouse click events to backend and apply game logic over it to display
     * updated board layout to player.
     * @param  polygonLabel The unique label of the polygon which is clicked by player
     * @return GameState which contains current game board layout and list of polygons to highlight
     **/
    @Override
    public GameState onClick(String polygonLabel) {
        try {
            // polygonPos must be in range [0, 95]
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
        GameState clickResponse = new GameState(getBoard(), highlightPolygonsList);
        if(board.isGameOver()) {
            Colour winningColour = board.getWinner();
            Map.Entry<Colour, String> winningPlayer = settings.getPlayerName(winningColour);
            Log.d(TAG, "Winner: " + winningColour);
            clickResponse.setGameOver(winningPlayer);
        }
        Log.d(TAG, "ClickResponse: "+clickResponse);
        return clickResponse;
    }

    /**
     * @return returns which colour turn it is currently and the player's name
     */
    @Override
    public Map.Entry<Colour, String> getTurn() {
        return this.settings.getPlayerName(this.board.getTurn());
    }

    /**
     *
     * @param settings Settings of the Game including the player names
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
