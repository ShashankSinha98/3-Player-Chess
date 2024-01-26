package model;

import com.google.common.collect.ImmutableSet;
import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

 class RookTest {
    private Board board;

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece rook = new Rook(Colour.BLUE);
        assertNotEquals(0, rook.directions.length);
    }

     @ParameterizedTest
     @EnumSource(Colour.class)
     void isLegalMove_rookMovesToEmptySquare_True(Colour colour) {
         Board board = new Board();
         board.boardMap.clear();

         Position rookPosition = BE2;

         BasePiece rook = new Rook(colour);
         board.boardMap.put(rookPosition, rook);

         assertTrue(rook.isLegalMove(board.boardMap, rookPosition, BE4));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_rookTakesItsColourPiece_False(BasePiece piece) {
         BasePiece rook = new Rook(piece.colour);

         Position startPosition = BE2;
         Position endPosition = BE4;

         board.boardMap.put(startPosition, rook);
         board.boardMap.put(endPosition, piece);

         assertFalse(rook.isLegalMove(board.boardMap, startPosition, endPosition));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_rookTakesDifferentColourPiece_True(BasePiece piece) {
         BasePiece rook = new Rook(piece.colour.next());

         Position startPosition = BE2;
         Position endPosition = BE4;

         board.boardMap.put(startPosition, rook);
         board.boardMap.put(endPosition, piece);

         assertTrue(rook.isLegalMove(board.boardMap, startPosition, endPosition));
     }

     @ParameterizedTest
     @EnumSource(value = Position.class, names = {"BA1", "BH1", "RA1", "RH1", "GA1", "GH1"})
     void check_rookPresentInInitialPosition_True(Position position) {
         BasePiece piece = board.boardMap.get(position);
         assertInstanceOf(Rook.class, piece);
     }

     @ParameterizedTest
     @EnumSource(Colour.class)
     void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
         Board board = new Board();
         board.boardMap.clear();                 //empty board
         Position startPosition = BE4;

         BasePiece rook = new Rook(colour);
         board.boardMap.put(startPosition, rook);

         Set<Position> expectedRookMoves =
                 ImmutableSet.of(BE1, BE2, BE3, BA4, BB4, BC4, BD4, BF4, BG4, BH4, RD4, RD3, RD2, RD1);
         Set<Position> actualRookMoves = rook.getHighlightPolygons(board.boardMap, startPosition);

         assertEquals(expectedRookMoves, actualRookMoves);
     }

    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initRookAllColours_correctStringFormat(Colour colour) {
        BasePiece rook = new Rook(colour);
        String expectedFormat = colour.toString() + "R";

        assertEquals(expectedFormat, rook.toString());
    }
}
