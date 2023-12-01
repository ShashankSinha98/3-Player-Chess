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
package jchess.core.pieces;

import jchess.core.Chessboard;
// import jchess.core.Colors;
// import jchess.core.pieces.Bishop;
// import jchess.core.pieces.Knight;
// import jchess.core.pieces.Queen;
// import jchess.core.pieces.Rook;
import jchess.core.Player;

/**
 * @author Mateusz Sławomir Lach (matlak, msl)
 */
public class PieceFactory 
{

    // public static final Piece getPiece(Chessboard chessboard, Colors color, String pieceType, Player player) 
    // {
    //     return PieceFactory.getPiece(chessboard, color.getColorName(), pieceType, player);
    // }
    
    public static final Piece getPiece(Chessboard chessboard, String pieceType, Player player) 
    {
        Piece piece = null;
        switch (pieceType)
        {
            case "Pawn":
                piece = new Pawn(chessboard, player);
                break;  
            case "Queen":
                piece = new Queen(chessboard, player);
                break;
            case "Rook":
                piece = new Rook(chessboard, player);
                break;
            case "Bishop":
                piece = new Bishop(chessboard, player);
                break;
            case "Knight":
                piece = new Knight(chessboard, player);
                break; 
            case "King":
                piece = new King(chessboard, player);
                break;  
        }
        return piece;
    }
    
}
