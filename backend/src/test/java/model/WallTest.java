package model;

import com.google.common.collect.ImmutableSet;
import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class WallTest {

     private Board board;
     private Map<Position, BasePiece> boardMap;

     @BeforeEach
     void initBeforeEachBoardTest() {
         board = new Board();
         boardMap = board.boardMap;
     }

    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece wall = new Wall(Colour.BLUE);
        assertNotEquals(0, wall.directions.length);
    }

     @ParameterizedTest
     @EnumSource(value = Position.class, names = {"BH2", "RH2", "GH2"})
     void check_wallPresentInInitialPosition_True(Position position) {
         BasePiece piece = boardMap.get(position);
         assertInstanceOf(Wall.class, piece);
     }

     @ParameterizedTest
     @EnumSource(Colour.class)
     void isLegalMove_wallMovesToEmptySquare_True(Colour colour) {
         Board board = new Board();
         boardMap.clear();

         Position wallPosition = BE2;

         BasePiece wall = new Wall(colour);
         boardMap.put(wallPosition, wall);

         assertTrue(wall.isLegalMove(boardMap, wallPosition, BE4));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_wallTakesItsColourPiece_False(BasePiece piece) {
         BasePiece wall = new Wall(piece.colour);

         Position startPosition = BE2;
         Position endPosition = BE4;

         boardMap.put(startPosition, wall);
         boardMap.put(endPosition, piece);

         assertFalse(wall.isLegalMove(boardMap, startPosition, endPosition));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_wallTakesDifferentColourPiece_False(BasePiece piece) {
         BasePiece wall = new Wall(piece.colour.next());

         Position startPosition = BE2;
         Position endPosition = BE4;

         boardMap.put(startPosition, wall);
         boardMap.put(endPosition, piece);

         assertFalse(wall.isLegalMove(boardMap, startPosition, endPosition));
     }

     @ParameterizedTest
     @EnumSource(Colour.class)
     void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
         Board board = new Board();
         boardMap.clear();                 //empty board
         Position startPosition = BE4;

         BasePiece wall = new Wall(colour);
         boardMap.put(startPosition, wall);

         Set<Position> expectedWallMoves =
                 ImmutableSet.of(BE1, BE2, BE3, BA4, BB4, BC4, BD4, BF4, BG4, BH4, RD4, RD3, RD2, RD1);
         Set<Position> actualWallMoves = wall.getHighlightPolygons(boardMap, startPosition);

         assertEquals(expectedWallMoves, actualWallMoves);
     }

    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initWallAllColours_correctStringFormat(Colour colour) {
        BasePiece wall = new Wall(colour);
        String expectedFormat = colour.toString() + "W";

        assertEquals(expectedFormat, wall.toString());
    }
}
