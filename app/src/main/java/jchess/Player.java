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
package jchess;

import java.io.Serializable;


/**
 * Class representing the player in the game
 */
public class Player implements Serializable
{

    String name;


    private Colors color;

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public enum playerTypes
    {

        localUser, networkUser, computer
    }
    public playerTypes playerType;
    public boolean goDown;

    public Player()
    {
    }
    //TODO
    public Player(String name, String color)
    {
        this.name = name;
        this.color = Colors.valueOf(color.toUpperCase());
        this.goDown = false;
    }

    /** Method setting the players name
     *  @param name name of player
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /** Method getting the players name
     *  @return name of player
     */
    String getName()
    {
        return this.name;
    }

    /** Method setting the players type
     *  @param type type of player - enumerate
     */
    public void setType(playerTypes type)
    {
        this.playerType = type;
    }
}
