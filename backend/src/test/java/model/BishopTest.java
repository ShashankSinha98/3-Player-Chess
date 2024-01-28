package model;

import com.google.common.collect.ImmutableSet;
import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the Bishop class.
 */
 class BishopTest {

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
    * Tests the setupDirections method, expecting the bishop movement directions to be non-empty.
    */
    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece bishop = new Bishop(Colour.BLUE);
        assertNotEquals(0, bishop.directions.length);
    }

   /**
    * Parameterized test for isLegalMove method with valid moves, expecting true.
    *
    * @param start Starting position of the bishop
    * @param end   Ending position of the bishop
    */
    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/legalBishopMoves.csv")
     void isLegalMove_validMoves_True(String start, String end) {
        Position startPosition = Position.valueOf(start);
        Position endPosition = Position.valueOf(end);

        BasePiece bishop = new Bishop(startPosition.getColour());
        boardMap.put(startPosition, bishop);

       Set<Position> actualBishopMoves = bishop.getHighlightPolygons(boardMap, startPosition);
       assertTrue(actualBishopMoves.contains(endPosition));
    }

    /**
     * Parameterized test for isLegalMove method with invalid moves, expecting false.
     *
     * @param start Starting position of the bishop
     * @param end   Ending position of the bishop
     */
    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/illegalBishopMoves.csv")
     void isLegalMove_invalidMoves_False(String start, String end) {
        Position startPosition = Position.valueOf(start);
        Position endPosition = Position.valueOf(end);

        BasePiece bishop = new Bishop(startPosition.getColour());
        boardMap.put(startPosition, bishop);

       Set<Position> actualBishopMoves = bishop.getHighlightPolygons(boardMap, startPosition);
       assertFalse(actualBishopMoves.contains(endPosition));
    }

    /**
     * Parameterized test to check if the bishop is present in its initial position, expecting true.
     *
     * @param position Initial position of the bishop
     */
    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"BC1", "BF1", "RC1", "RF1", "GC1", "GF1"})
     void check_bishopPresentInInitialPosition_True(Position position) {
        BasePiece piece = boardMap.get(position);
        assertInstanceOf(Bishop.class, piece);
    }


    /**
     * Parameterized test for isLegalMove method when bishop takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_bishopTakesItsColourPiece_False(BasePiece piece) {
        BasePiece bishop = new Bishop(piece.colour);

        boardMap.put(BE4, bishop);
        boardMap.put(BD3, piece);

       Set<Position> actualBishopMoves = bishop.getHighlightPolygons(boardMap, BE4);
       assertFalse(actualBishopMoves.contains(BD3));
    }

    /**
     * Parameterized test for isLegalMove method when bishop takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_bishopTakesDifferentColourPiece_True(BasePiece piece) {
        BasePiece bishop = new Bishop(piece.colour.next());
        boardMap.put(BE4, bishop);

        boardMap.put(BD3, piece);
       Set<Position> actualBishopMoves = bishop.getHighlightPolygons(boardMap, BE4);
       assertTrue(actualBishopMoves.contains(BD3));
    }

    /**
     * Parameterized test for getHighlightPolygons method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the bishop
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
     void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
        boardMap.clear();                 //empty board
        Position startPosition = BE4;

        BasePiece bishop = new Bishop(colour);
        boardMap.put(startPosition, bishop);

        Set<Position> expectedBishopMoves =
                ImmutableSet.of(BH1, BG2, BF3, BD3, BC2, BB1, RC4, RB3, RA2, GE4, GF3, GG2, GH1, RE4, RF3, RG2, RH1);
        Set<Position> actualBishopMoves = bishop.getHighlightPolygons(boardMap, startPosition);

        assertEquals(expectedBishopMoves, actualBishopMoves);
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for bishop initialization.
     * BB: blue Bishop
     * GB: green Bishop
     * RB: red Bishop
     * @param colour Colour of the bishop
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
     void toString_initBishopAllColours_correctStringFormat(Colour colour) {
        BasePiece bishop = new Bishop(colour);
        String expectedFormat = colour.toString() + "B";

        assertEquals(expectedFormat, bishop.toString());
    }
}
