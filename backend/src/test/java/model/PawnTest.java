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

 class PawnTest {

     private Board board;
     private Map<Position, BasePiece> boardMap;

     @BeforeEach
     void initBeforeEachBoardTest() {
         board = new Board();
         boardMap = board.boardMap;
     }

    // Naming Convention- MethodName_StateUnderTest_ExpectedBehavior
    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece pawn = new Pawn(Colour.BLUE);
        assertNotEquals(0, pawn.directions.length);
    }

     @Test
     void isLegalMove_pawnMoveForwardToEmptySquare_True() {
         BasePiece pawn = new Pawn(Colour.BLUE);
         boardMap.put(BE4, pawn);
         Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, BE4);
         assertTrue(actualPawnMoves.contains(RD4));
     }

    @ParameterizedTest
    @EnumSource(Colour.class)
     void isLegalMove_pawnAbsentFromStartPosition_False(Colour colour) {
        BasePiece pawn = new Pawn(colour);
        Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, BE4);
        assertFalse(actualPawnMoves.contains(BD3));
    }

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

     @Test
     void getHighlightPolygons_pawnInitialPosition_presentInPolygonList() {
         Position startPosition = BB2;

         BasePiece pawn = new Pawn(startPosition.getColour());

         Set<Position> expectedPawnMoves = ImmutableSet.of(BB3, BB4);
         Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, startPosition);

        assertEquals(expectedPawnMoves, actualPawnMoves);
    }

     @Test
     void getHighlightPolygons_pawnAlreadyMoved_presentInPolygonList() {
         Position startPosition = BE4;

         BasePiece pawn = new Pawn(startPosition.getColour());

         Set<Position> expectedPawnMoves = ImmutableSet.of(RD4);
         Set<Position> actualPawnMoves = pawn.getHighlightPolygons(boardMap, startPosition);

         assertEquals(expectedPawnMoves, actualPawnMoves);
     }

    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initPawnAllColours_correctStringFormat(Colour colour) {
        BasePiece pawn = new Pawn(colour);
        String expectedFormat = colour.toString() + "P";

        assertEquals(expectedFormat, pawn.toString());
    }

}
