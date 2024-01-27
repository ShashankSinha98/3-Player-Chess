package model;

import com.google.common.collect.ImmutableSet;
import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the Jester class.
 */
class JesterTest {

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
     * Tests the setupDirections method, expecting the piece directions to be non-empty.
     */
    @Test
    void setupDirections_initPieceDirectionsIsEmpty_False(){
        BasePiece jester = new Jester(Colour.BLUE);
        assertNotEquals(0, jester.directions.length);
    }

    /**
     * Parameterized test for isLegalMove method when jester moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the jester
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_jesterMovesToEmptySquare_True(Colour colour) {
        Board board = new Board();
        boardMap.clear();

        Position jesterPosition = BE2;

        BasePiece jester = new Jester(colour);
        boardMap.put(jesterPosition, jester);

        Set<Position> actualJesterMoves = jester.getHighlightPolygons(boardMap, jesterPosition);
        assertTrue(actualJesterMoves.contains(BF4));
    }

    /**
     * Parameterized test for isLegalMove method when jester takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("model.DataProvider#pieceProvider")
    void isLegalMove_jesterTakesItsColourPiece_False(BasePiece piece) {
        BasePiece jester = new Jester(piece.colour);

        boardMap.put(BE4, jester);
        boardMap.put(BC3, piece);

        Set<Position> actualJesterMoves = jester.getHighlightPolygons(boardMap, BE4);
        assertFalse(actualJesterMoves.contains(BC3));
    }

    /**
     * Parameterized test for isLegalMove method when jester takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("model.DataProvider#pieceProvider")
    void isLegalMove_jesterTakesDifferentColourPiece_True(BasePiece piece) {
        BasePiece jester = new Jester(piece.colour.next());
        boardMap.put(BE4, jester);
        boardMap.put(BC3, piece);
        Set<Position> actualJesterMoves = jester.getHighlightPolygons(boardMap, BE4);
        assertTrue(actualJesterMoves.contains(BC3));
    }


    /**
     * Parameterized test for getHighlightPolygons method,
     * expecting valid polygons to be present in the list of all valid moves.
     *
     * @param colour Colour of the jester
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour){
        Board board = new Board();
        boardMap.clear();
        Position startPosition = BE4;

        BasePiece jester = new Jester(colour);
        boardMap.put(startPosition, jester);

        Set<Position> expectedJesterMoves = ImmutableSet.of(BG3, BF2, BD2, BC3, GF4, GE3, RB4, RC3, RF4, RE3);
        Set<Position> actualJesterMoves = jester.getHighlightPolygons(boardMap, startPosition);

        assertEquals(expectedJesterMoves, actualJesterMoves);
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for jester initialization.
     * BJ: blue Jester
     * GJ: green Jester
     * RJ: red Jester
     *
     * @param colour Colour of the jester
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initJesterAllColours_correctStringFormat(Colour colour) {
        BasePiece jester = new Jester(colour);
        String expectedFormat = colour.toString() + "J";

        assertEquals(expectedFormat, jester.toString());
    }
}