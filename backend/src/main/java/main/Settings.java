package main;

import common.Colour;

import java.util.HashMap;
import java.util.Map;
import static common.Colour.*;

public class Settings {

    private Map<Colour, String> playerNamesMap;

    public Settings() {
        playerNamesMap = new HashMap<>();
    }

    public void setBluePlayerName(String bluePlayerName) {
        this.playerNamesMap.put(BLUE, bluePlayerName);
    }

    public void setGreenPlayerName(String greenPlayerName) {
        this.playerNamesMap.put(GREEN, greenPlayerName);
    }

    public void setRedPlayerName(String redPlayerName) {
        this.playerNamesMap.put(RED, redPlayerName);
    }

    public Map.Entry<Colour, String> getPlayerName(Colour colour){
        for (Map.Entry<Colour, String> entry : playerNamesMap.entrySet()) {
            if (entry.getKey() == colour) {
                return entry;
            }
        }
        return null;
    }
}
