/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess.util;

import java.io.Serializable;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import jchess.core.Colors;
import jchess.core.Player;

/** Class representings game settings available for the current player
 */
public class Settings implements Serializable
{

    private static ResourceBundle loc = null;
    private int timeForGame;
    private boolean runningChat;
    private boolean runningGameClock;
    private boolean timeLimitSet;//tel us if player choose time 4 game or it's infinity
    private boolean upsideDown;
    private gameModes gameMode;
    private Player playerWhite;
    private Player playerBlack;
    private gameTypes gameType;
    private boolean renderLabels = true;

    public enum gameModes
    {
        newGame, loadGame
    }

    public enum gameTypes
    {
        local, network
    }

    public Settings()
    {
        //temporally
        this.playerWhite = new Player("", Colors.WHITE.getColorName());
        this.playerBlack = new Player("", Colors.BLACK.getColorName());
        this.timeLimitSet = false;

        gameMode = gameModes.newGame;
    }
    
    /**
     * @return the runningChat
     */
    public boolean isRunningChat()
    {
        return runningChat;
    }

    /**
     * @return the runningGameClock
     */
    public boolean isRunningGameClock()
    {
        return runningGameClock;
    }

    /**
     * @return the timeLimitSet
     */
    public boolean isTimeLimitSet()
    {
        return timeLimitSet;
    }

    /**
     * @return the isUpsideDown
     */
    public boolean isUpsideDown()
    {
        return upsideDown;
    }

    /**
     * @return the gameMode
     */
    public gameModes getGameMode()
    {
        return gameMode;
    }

    /**
     * @return the playerWhite
     */
    public Player getPlayerWhite()
    {
        return playerWhite;
    }

    /**
     * @return the playerBlack
     */
    public Player getPlayerBlack()
    {
        return playerBlack;
    }
    
    public void setPlayerWhite(Player player)
    {
        this.playerWhite = player;
    }
    
    public void setPlayerBlack(Player player)
    {
        this.playerBlack = player;
    }

    /**
     * @return the gameType
     */
    public gameTypes getGameType()
    {
        return gameType;
    }

    /**
     * @return the renderLabels
     */
    public boolean isRenderLabels()
    {
        return renderLabels;
    }
     
    public void setRenderLabels(boolean renderLabels)
    {
        this.renderLabels = renderLabels;
    }

    /**
     * @param upsideDown the upsideDown to set
     */
    public void setUpsideDown(boolean upsideDown)
    {
        this.upsideDown = upsideDown;
    }

    /**
     * @param gameMode the gameMode to set
     */
    public void setGameMode(gameModes gameMode)
    {
        this.gameMode = gameMode;
    }

    /**
     * @param gameType the gameType to set
     */
    public void setGameType(gameTypes gameType)
    {
        this.gameType = gameType;
    }

    /**
     * @param timeForGame the timeForGame to set
     */
    public void setTimeForGame(int timeForGame)
    {
        this.timeLimitSet = true;
        this.timeForGame = timeForGame;
    }

    /** Method to get game time set by player
     *  @return timeFofGame int with how long the game will leasts
     */
    public int getTimeForGame()
    {
        return this.timeForGame;
    }

    public static String lang(String key)
    {
        if (Settings.loc == null)
        {
            Settings.loc = PropertyResourceBundle.getBundle("jchess.resources.i18n.main");
            Locale.setDefault(Locale.ENGLISH);
        }
        String result = "";
        try
        {
            result = Settings.loc.getString(key);
        }
        catch (java.util.MissingResourceException exc)
        {
            result = key;
        }
        System.out.println(Settings.loc.getLocale().toString());
        return result;
    }
}
