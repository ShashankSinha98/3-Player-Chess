package utility;

import common.Colour;

import java.util.HashMap;
import java.util.Map;
import static common.Colour.*;

/**
 * Represents settings related to player names in a game.
 * Provides methods to set and retrieve player names based on their colours.
 */
public class Settings {

    /**
     * A map that associates player colours with their respective names.
     * The keys are of type {@link Colour}, representing the player colours,
     * and the values are of type {@link String}, representing the player names.
     */
    private Map<Colour, String> playerNamesMap;

    public Settings() {
        playerNamesMap = new HashMap<>();
    }

    /**
     * Sets the name of the blue player
     * @param bluePlayerName name of the blue player
     */
    public void setBluePlayerName(String bluePlayerName) {
        this.playerNamesMap.put(BLUE, bluePlayerName);
    }

    /**
     * Sets the name of the green player
     * @param greenPlayerName name of the green player
     */
    public void setGreenPlayerName(String greenPlayerName) {
        this.playerNamesMap.put(GREEN, greenPlayerName);
    }

    /**
     * Sets the name of the red player
     *
     * @param redPlayerName name of the red player
     */
    public void setRedPlayerName(String redPlayerName) {
        this.playerNamesMap.put(RED, redPlayerName);
    }

    /**
     * Gets the player name for the specified colour.
     *
     * @param colour the colour of the player whose name is to be retrieved
     * @return a Map.Entry containing the colour and the corresponding player name,
     *         or null if no player name is found for the specified colour
     */
    public Map.Entry<Colour, String> getPlayerName(Colour colour){
        for (Map.Entry<Colour, String> entry : playerNamesMap.entrySet()) {
            if (entry.getKey() == colour) {
                return entry;
            }
        }
        return null;
    }
}
