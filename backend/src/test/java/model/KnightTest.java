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
import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    // Naming Convention- MethodName_StateUnderTest_ExpectedBehavior
    private Board board;

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    @Test
    void setupDirections_initPieceDirectionsIsEmpty_False(){
        BasePiece knight = new Knight(Colour.BLUE);
        assertNotEquals(0, knight.directions.length);
    }

    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_knightMovesToEmptySquare_True(Colour colour) {
        Board board = new Board();
        board.boardMap.clear();

        Position knightPosition = BE2;

        BasePiece knight = new Knight(colour);
        board.boardMap.put(knightPosition, knight);

        assertTrue(knight.isLegalMove(board, knightPosition, BF4));
    }

    @ParameterizedTest
    @MethodSource("model.DataProvider#pieceProvider")
    void isLegalMove_knightTakesItsColourPiece_False(BasePiece piece) {
        BasePiece knight = new Knight(piece.colour);

        board.boardMap.put(BE4, knight);
        board.boardMap.put(BC3, piece);

        assertFalse(knight.isLegalMove(board, BE4, BC3));
    }

    @ParameterizedTest
    @MethodSource("model.DataProvider#pieceProvider")
    void isLegalMove_knightTakesDifferentColourPiece_True(BasePiece piece) {
        BasePiece knight = new Knight(piece.colour.next());
        board.boardMap.put(BE4, knight);

        board.boardMap.put(BC3, piece);

        assertTrue(knight.isLegalMove(board, BE4, BC3));
    }


    @ParameterizedTest
    @EnumSource(Colour.class)
    void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour){
        Board board = new Board();
        board.boardMap.clear();
        Position startPosition = BE4;

        BasePiece knight = new Knight(colour);
        board.boardMap.put(startPosition, knight);

        Set<Position> expectedKnightMoves = ImmutableSet.of(BG3, BF2, BD2, BC3, GF4, GE3, RB4, RC3, RF4, RE3);
        Set<Position> actualKnightMoves = knight.getHighlightPolygons(board, startPosition);

        assertEquals(expectedKnightMoves, actualKnightMoves);
    }

    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initKnightAllColours_correctStringFormat(Colour colour) {
        BasePiece knight = new Knight(colour);
        String expectedFormat = colour.toString() + "N";

        assertEquals(expectedFormat, knight.toString());
    }
}