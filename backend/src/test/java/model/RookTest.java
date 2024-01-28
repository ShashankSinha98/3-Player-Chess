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

/**
 * Test class for the {@link Rook} class.
 * Contains various tests to verify the behavior of the Rook piece in the game.
 */
 class RookTest {

     private Board board;
     private Map<Position, BasePiece> boardMap;

    /**
     * Initializes a new Board instance before each test.
     */
     @BeforeEach
     void initBeforeEachBoardTest() {
         board = new Board();
         boardMap = board.boardMap;
     }

    /**
     * Tests the setupDirections method,
     * expecting the piece directions to be non-empty.
     */
    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece rook = new Rook(Colour.BLUE);
        assertNotEquals(0, rook.directions.length);
    }

    /**
     * Parameterized test for isLegalMove method when the rook moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the rook
     */
     @ParameterizedTest
     @EnumSource(Colour.class)
     void isLegalMove_rookMovesToEmptySquare_True(Colour colour) {
         Board board = new Board();
         boardMap.clear();

         Position rookPosition = BE2;

         BasePiece rook = new Rook(colour);
         boardMap.put(rookPosition, rook);
         Set<Position> actualRookMoves = rook.getHighlightPolygons(boardMap, rookPosition);
         assertTrue(actualRookMoves.contains(BE4));
     }

    /**
     * Parameterized test for isLegalMove method
     * when the rook takes a piece of its own colour,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_rookTakesItsColourPiece_False(BasePiece piece) {
         BasePiece rook = new Rook(piece.colour);

         Position startPosition = BE2;
         Position endPosition = BE4;

         boardMap.put(startPosition, rook);
         boardMap.put(endPosition, piece);
         Set<Position> actualRookMoves = rook.getHighlightPolygons(boardMap, startPosition);
         assertFalse(actualRookMoves.contains(endPosition));
     }

    /**
     * Parameterized test for isLegalMove method
     * when the rook takes a piece of a different colour,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_rookTakesDifferentColourPiece_True(BasePiece piece) {
         BasePiece rook = new Rook(piece.colour.next());

         Position startPosition = BE2;
         Position endPosition = BE4;

         boardMap.put(startPosition, rook);
         boardMap.put(endPosition, piece);
         Set<Position> actualRookMoves = rook.getHighlightPolygons(boardMap, startPosition);
         assertTrue(actualRookMoves.contains(endPosition));
     }

    /**
     * Parameterized test for checking if the rook is present in its initial position,
     * expecting true.
     *
     * @param position Initial position of the rook
     */
     @ParameterizedTest
     @EnumSource(value = Position.class, names = {"BA1", "BH1", "RA1", "RH1", "GA1", "GH1"})
     void check_rookPresentInInitialPosition_True(Position position) {
         BasePiece piece = boardMap.get(position);
         assertInstanceOf(Rook.class, piece);
     }

    /**
     * Parameterized test for getHighlightPolygons method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the rook
     */
     @ParameterizedTest
     @EnumSource(Colour.class)
     void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
         Board board = new Board();
         boardMap.clear();                 //empty board
         Position startPosition = BE4;

         BasePiece rook = new Rook(colour);
         boardMap.put(startPosition, rook);

         Set<Position> expectedRookMoves =
                 ImmutableSet.of(BE1, BE2, BE3, BA4, BB4, BC4, BD4, BF4, BG4, BH4, RD4, RD3, RD2, RD1);
         Set<Position> actualRookMoves = rook.getHighlightPolygons(boardMap, startPosition);

         assertEquals(expectedRookMoves, actualRookMoves);
     }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for rook initialization.
     * BR: blue Rook
     * GR: green Rook
     * RR: red Rook
     *
     * @param colour Colour of the rook
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initRookAllColours_correctStringFormat(Colour colour) {
        BasePiece rook = new Rook(colour);
        String expectedFormat = colour.toString() + "R";

        assertEquals(expectedFormat, rook.toString());
    }
}
