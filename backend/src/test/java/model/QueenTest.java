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
import static org.junit.jupiter.api.Assertions.*;

 class QueenTest {

     private Board board;
     private Map<Position, BasePiece> boardMap;

     @BeforeEach
     void initBeforeEachBoardTest() {
         board = new Board();
         boardMap = board.boardMap;
     }

    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece queen = new Queen(Colour.BLUE);
        assertNotEquals(0, queen.directions.length);
    }

     @ParameterizedTest
     @EnumSource(value = Position.class, names = {"BD1", "RD1", "GD1"})
     void check_queenPresentInInitialPosition_True(Position position) {
         BasePiece piece = boardMap.get(position);
         assertInstanceOf(Queen.class, piece);
     }

     @ParameterizedTest
     @EnumSource(Colour.class)
     void isLegalMove_queenMovesToEmptySquare_True(Colour colour) {
         Board board = new Board();
         boardMap.clear();

         Position queenPosition = BE4;

         BasePiece queen = new Queen(colour);
         boardMap.put(queenPosition, queen);
         Set<Position> actualQueenMoves = queen.getHighlightPolygons(boardMap, queenPosition);
         assertTrue(actualQueenMoves.contains(GF3));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_queenTakesItsColourPiece_False(BasePiece piece) {
         BasePiece queen = new Queen(piece.colour);

         Position startPosition = BE4;
         Position endPosition = GF3;

         boardMap.put(startPosition, queen);
         boardMap.put(endPosition, piece);
         Set<Position> actualQueenMoves = queen.getHighlightPolygons(boardMap, startPosition);
         assertFalse(actualQueenMoves.contains(endPosition));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_queenTakesDifferentColourPiece_True(BasePiece piece) {
         BasePiece queen = new Queen(piece.colour.next());

         Position startPosition = BE4;
         Position endPosition = GF3;

         boardMap.put(startPosition, queen);
         boardMap.put(endPosition, piece);
         Set<Position> actualQueenMoves = queen.getHighlightPolygons(boardMap, startPosition);
         assertTrue(actualQueenMoves.contains(endPosition));
     }

     @ParameterizedTest
     @EnumSource(Colour.class)
     void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
         Board board = new Board();
         boardMap.clear();                 //empty board
         Position startPosition = BE4;

         BasePiece queen = new Queen(colour);
         boardMap.put(startPosition, queen);

         Set<Position> expectedQueenMoves =
                 ImmutableSet.of(RD2, BE1, GH1, RA2, BD3, RC4, BC4, BH4, RF3, BF3, RD1, RD3, RG2, GE4, RE4, RH1, BE3, BB4, BH1, BE2, BB1, BA4, BD4, RD4, GG2, BG2, BG4, GF3, BC2, RB3, BF4);
         Set<Position> actualQueenMoves = queen.getHighlightPolygons(boardMap, startPosition);

         assertEquals(expectedQueenMoves, actualQueenMoves);
     }

    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initQueenAllColours_correctStringFormat(Colour colour) {
        BasePiece queen = new Queen(colour);
        String expectedFormat = colour.toString() + "Q";

        assertEquals(expectedFormat, queen.toString());
    }
}
