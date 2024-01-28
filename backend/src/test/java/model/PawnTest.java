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
 * Test class for the {@link Pawn} class.
 * Contains various tests to verify the behavior of the Pawn piece in the game.
 */
 class PawnTest {

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
     * expecting the pawn movement directions to be non-empty.
     */
    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece pawn = new Pawn(Colour.BLUE);
        assertNotEquals(0, pawn.directions.length);
    }

    /**
     * Tests the isLegalMove method when a pawn moves forward to an empty square,
     * expecting true.
     */
     @Test
     void isLegalMove_pawnMoveForwardToEmptySquare_True() {
         BasePiece pawn = new Pawn(Colour.BLUE);
         boardMap.put(BE4, pawn);
         Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, BE4);
         assertTrue(actualPawnMoves.contains(RD4));
     }

    /**
     * Parameterized test for isLegalMove method
     * when a pawn is absent from its starting position,
     * expecting false.
     *
     * @param colour Colour of the pawn
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
     void isLegalMove_pawnAbsentFromStartPosition_False(Colour colour) {
        BasePiece pawn = new Pawn(colour);
        Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, BE4);
        assertFalse(actualPawnMoves.contains(BD3));
    }

    /**
     * Parameterized test for isLegalMove method
     * when a pawn moves forward to take an opponent's piece,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_pawnMoveForwardToTakeOpponentPiece_False(BasePiece piece) {
        BasePiece pawn = new Pawn(Colour.BLUE);
        Position startPosition = BE4;
        Position endPosition = RD4;

        boardMap.put(startPosition, pawn);

        boardMap.put(endPosition, piece);
        Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, startPosition);
        assertFalse(actualPawnMoves.contains(endPosition));
    }

    /**
     * Parameterized test for isLegalMove method
     * when a pawn moves diagonally to take an opponent's piece,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_pawnMoveDiagonalToTakeOpponentPiece_True(BasePiece piece) {
        if(piece.getColour()==Colour.BLUE) return;

        BasePiece pawn = new Pawn(Colour.BLUE);

        Position startPosition = BE4;
        Position endPosition = RC4;

        boardMap.put(startPosition, pawn);

        boardMap.put(endPosition, piece);
        Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, startPosition);
        assertTrue(actualPawnMoves.contains(endPosition));
    }

    /**
     * Parameterized test for isLegalMove method
     * when a pawn takes a piece of its own colour,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_pawnTakesItsColourPiece_False(BasePiece piece) {
        if(piece.getColour() != Colour.BLUE) return;

        BasePiece pawn = new Pawn(Colour.BLUE);
        Position startPosition = BE4;
        Position endPosition = RC4;

        boardMap.put(startPosition, pawn);

        boardMap.put(endPosition, piece);
         Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, startPosition);
         assertFalse(actualPawnMoves.contains(endPosition));
    }

    /**
     * Tests the getHighlightPolygons method for a pawn in its initial position,
     * expecting valid polygons to be present in the list.
     */
     @Test
     void getHighlightPolygons_pawnInitialPosition_presentInPolygonList() {
         Position startPosition = BB2;

         BasePiece pawn = new Pawn(startPosition.getColour());

         Set<Position> expectedPawnMoves = ImmutableSet.of(BB3, BB4);
         Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, startPosition);

        assertEquals(expectedPawnMoves, actualPawnMoves);
    }

    /**
     * Tests the getHighlightPolygons method for a pawn that has already moved,
     * expecting valid polygons to be present in the list.
     */
     @Test
     void getHighlightPolygons_pawnAlreadyMoved_presentInPolygonList() {
         Position startPosition = BE4;

         BasePiece pawn = new Pawn(startPosition.getColour());

         Set<Position> expectedPawnMoves = ImmutableSet.of(RD4);
         Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, startPosition);

         assertEquals(expectedPawnMoves, actualPawnMoves);
     }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for pawn initialization.
     * BP: blue Pawn
     * GP: green Pawn
     * RP: red Pawn
     *
     * @param colour Colour of the pawn
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initPawnAllColours_correctStringFormat(Colour colour) {
        BasePiece pawn = new Pawn(colour);
        String expectedFormat = colour.toString() + "P";

        assertEquals(expectedFormat, pawn.toString());
    }

}
