package abstraction;
import common.Colour;
import common.OnClickResponse;

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
     * @param  polygonLabel The unique label of the polygon which is clicked by player
     * @return OnClickResponse which contains current game board layout and list of polygons to highlight
     **/
    OnClickResponse onClick(String polygonLabel);

    /**
     * @return returns which colour turn it is currently
     * */
    Colour getTurn();
}
