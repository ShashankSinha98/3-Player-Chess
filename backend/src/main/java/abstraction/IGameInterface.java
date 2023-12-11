package abstraction;
import common.Colour;
import common.ImpossiblePositionException;

import java.util.List;
import java.util.Map;

public interface IGameInterface {
	
    /**
     * Get the current board map being used by backend for current game session
     * @return Board map
     * */
    Map<String, String> getBoard();

    /**
     * Responsible for sending mouse click events to backend and apply game logic over it to display
     * updated board layout to player.
     * @param  squarePos The unique position of the square which is clicked by player
     * @return Board map which will be rendered by web view
     * @throws ImpossiblePositionException if the square position is outside range [0, 95]
     * // TODO: Currently blindly moves the piece
     * */
    Map<String, String> onClick(int squarePos) throws ImpossiblePositionException;

    /**
     * @return returns which colour turn it is currently
     * TODO: Currently hardcoded and return only BLUE
     * */
    Colour getTurn();

    /**
     * Unique square positions which will be highlighted when user click on a valid sqaure
     * to tell him possible valid moves
     *
     * @return list of square positions
     * TODO: Currently return hardcoded values
     */
    List<String> getHighlightSquarePositions();
}