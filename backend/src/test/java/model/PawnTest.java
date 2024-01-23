package model;

import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

 class PawnTest {

    private Board board;

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    // Naming Convention- MethodName_StateUnderTest_ExpectedBehavior
    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece pawn = new Pawn(Colour.BLUE);
        assertNotEquals(0, pawn.directions.length);
    }

    @Test
     void isLegalMove_validMoves_True() {
        Position[] startPositions = new Position[] {BH2, BE4};
        Position[][] endPositions = new Position[][] {{BH3, BH4}, {RD4}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece pawn = new Pawn(start.getColour());
            board.boardMap.put(start, pawn);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertTrue(pawn.isLegalMove(board, start, end));
            }
        }
    }

    @Test
     void isLegalMove_invalidMoves_False() {
        Position[] startPositions = new Position[] {BH2, BE4};
        Position[][] endPositions = new Position[][] {{BG3, BH1}, {BE3}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece pawn = new Pawn(start.getColour());
            board.boardMap.put(start, pawn);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertFalse(pawn.isLegalMove(board, start, end));
            }
        }
    }

    @Test
     void isLegalMove_pawnAbsentFromStartPosition_False() {
        BasePiece pawn = new Pawn(Colour.BLUE);
        assertFalse(pawn.isLegalMove(board, BE4, BD3));
    }

    @Test
     void isLegalMove_pawnMoveForwardToTakeOpponentPiece_False() {
        BasePiece pawn = new Pawn(Colour.BLUE);
        board.boardMap.put(BE4, pawn);

        BasePiece king = new King(Colour.RED);
        board.boardMap.put(RD4, king);

        assertFalse(pawn.isLegalMove(board, BE4, RD4));
    }

    @Test
     void isLegalMove_pawnMoveDiagonalToTakeOpponentPiece_True() {
        BasePiece pawn = new Pawn(Colour.BLUE);
        board.boardMap.put(BE4, pawn);

        BasePiece king = new King(Colour.RED);
        board.boardMap.put(RC4, king);

        assertTrue(pawn.isLegalMove(board, BE4, RC4));
    }

    @Test
     void isLegalMove_pawnTakesItsColourPiece_False() {
        BasePiece bluePawn = new Pawn(Colour.BLUE);
        board.boardMap.put(BE4, bluePawn);

        BasePiece blueKing = new King(Colour.BLUE);
        board.boardMap.put(RC4, blueKing);

        assertFalse(bluePawn.isLegalMove(board, BE4, RC4));
    }

    @Test
     void getHighlightPolygons_validPolygons_presentInPolygonList() {
        Position[] startPositions = new Position[] {BH2, BE4};
        Position[][] endPositions = new Position[][] {{BH3, BH4}, {RD4}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece pawn = new Pawn(start.getColour());
            board.boardMap.put(start, pawn);
            Position[] ends = endPositions[i];

            Set<Position> highlightedPolygons = pawn.getHighlightPolygons(board, start);
            for(Position end: ends) {
                assertTrue(highlightedPolygons.contains(end));
            }
        }
    }

    @Test
     void getHighlightPolygons_invalidPolygons_presentInPolygonList() {
        Position[] startPositions = new Position[] {BH2, BE4};
        Position[][] endPositions = new Position[][] {{BG3, BH1}, {BE3}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece pawn = new Pawn(start.getColour());
            board.boardMap.put(start, pawn);
            Position[] ends = endPositions[i];

            Set<Position> highlightedPolygons = pawn.getHighlightPolygons(board, start);
            for(Position end: ends) {
                assertFalse(highlightedPolygons.contains(end));
            }
        }
    }

    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initPawnAllColours_correctStringFormat(Colour colour) {
        BasePiece pawn = new Pawn(colour);
        String expectedFormat = colour.toString() + "P";

        assertEquals(expectedFormat, pawn.toString());
    }

}
