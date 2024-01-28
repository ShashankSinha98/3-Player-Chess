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

/**
 * This class contains unit tests for the King class.
 */
 class KingTest {

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
     * expecting the King movement directions to be non-empty.
     */
    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece king = new King(Colour.GREEN);
        assertNotEquals(0, king.directions.length);
    }

    /**
     * Parameterized test to check if the king is present in its initial position,
     * expecting true.
     *
     * @param position Initial position of the king
     */
    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"BE1", "RE1", "GE1"})
    void check_kingPresentInInitialPosition_True(Position position) {
        BasePiece piece = boardMap.get(position);
        assertInstanceOf(King.class, piece);
    }

    /**
     * Parameterized test to check if the king is not present in its initial position,
     * expecting false.
     *
     * @param position Initial position of the king
     */
    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"BD2", "RD2", "GD2"})
     void check_kingPresentInInitialPosition_False(Position position) {
        BasePiece piece = boardMap.get(position);
        assertFalse(piece instanceof King);
    }

    /**
     * Parameterized test for isLegalMove method when king moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the king
     */
     @ParameterizedTest
     @EnumSource(Colour.class)
     void isLegalMove_kingMovesToEmptySquare_True(Colour colour) {
         Board board = new Board();
         boardMap.clear();

         Position kingPosition = BE2;

         BasePiece king = new King(colour);
         boardMap.put(kingPosition, king);

         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
         assertTrue(actualKingMoves.contains(BE3));
     }

    /**
     * Parameterized test for isLegalMove method when king takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_kingTakesItsColourPiece_False(BasePiece piece) {
         BasePiece king = new King(piece.colour);

         Position startPosition = BE4;
         Position endPosition = BD3;

         boardMap.put(startPosition, king);
         boardMap.put(endPosition, piece);

         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, startPosition);
         assertFalse(actualKingMoves.contains(endPosition));
     }

    /**
     * Parameterized test for isLegalMove method when king takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_kingTakesDifferentColourPiece_True(BasePiece piece) {
         BasePiece king = new King(piece.colour.next());

         Position startPosition = BE4;
         Position endPosition = BD3;

         boardMap.put(startPosition, king);
         boardMap.put(endPosition, piece);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, startPosition);
         assertTrue(actualKingMoves.contains(endPosition));
     }

    /**
     * Parameterized test for getHighlightPolygons method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the king
     */
     @ParameterizedTest
     @EnumSource(Colour.class)
     void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
         Board board = new Board();
         boardMap.clear();                 //empty board
         Position startPosition = BE4;

         BasePiece king = new King(colour);
         boardMap.put(startPosition, king);

         Set<Position> expectedKingMoves =
                 ImmutableSet.of(GE4, BD3, RC4, BF3, BD4, BE3, RE4, BF4, RD4);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, startPosition);

         assertEquals(expectedKingMoves, actualKingMoves);
     }

    /**
     * Test for short castle legal move, expecting true.
     */
    @Test
    void isLegalMove_shortCastle_True() {
        Board board = new Board();
        boardMap.clear();

        Position kingPosition = RE1;
        Position rookPosition = RH1;

        BasePiece king = new King(kingPosition.getColour());
        BasePiece rook = new Rook(rookPosition.getColour());

        boardMap.put(kingPosition, king);
        boardMap.put(rookPosition, rook);
        Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
        assertTrue(actualKingMoves.contains(RG1));
    }

    /**
     * Test for short castle legal move when the square is occupied, expecting false.
     */
     @Test
     void isLegalMove_shortCastleOccupiedSquare_False() {
         Board board = new Board();
         boardMap.clear();

         Position kingPosition = RE1;
         Position rookPosition = RH1;
         Position knightPosition = RG1;

         BasePiece king = new King(kingPosition.getColour());
         BasePiece rook = new Rook(rookPosition.getColour());
         BasePiece knight = new Knight(knightPosition.getColour());

         boardMap.put(kingPosition, king);
         boardMap.put(rookPosition, rook);
         boardMap.put(knightPosition, knight);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
         assertFalse(actualKingMoves.contains(RG1));
     }

    /**
     * Test for long castle legal move, expecting true.
     */
     @Test
     void isLegalMove_longCastle_True() {
         Board board = new Board();
         boardMap.clear();

         Position kingPosition = RE1;
         Position rookPosition = RA1;

         BasePiece king = new King(kingPosition.getColour());
         BasePiece rook = new Rook(rookPosition.getColour());

         boardMap.put(kingPosition, king);
         boardMap.put(rookPosition, rook);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
         assertTrue(actualKingMoves.contains(RC1));
     }

    /**
     * Test for long castle legal move when the square is occupied, expecting false.
     */
     @Test
     void isLegalMove_longCastleOccupiedSquare_False() {
         Board board = new Board();
         boardMap.clear();

         Position kingPosition = RE1;
         Position rookPosition = RA1;
         Position knightPosition = RC1;

         BasePiece king = new King(kingPosition.getColour());
         BasePiece rook = new Rook(rookPosition.getColour());
         BasePiece knight = new Knight(knightPosition.getColour());

         boardMap.put(kingPosition, king);
         boardMap.put(rookPosition, rook);
         boardMap.put(knightPosition, knight);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
         assertFalse(actualKingMoves.contains(RC1));
     }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for king initialization.
     * BK: blue King
     * GK: green King
     * RK: red King
     *
     * @param colour Colour of the king
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initKingAllColours_correctStringFormat(Colour colour) {
        BasePiece king = new King(colour);
        String expectedFormat = colour.toString() + "K";

        assertEquals(expectedFormat, king.toString());
    }
}
