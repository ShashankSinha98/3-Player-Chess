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

 class BishopTest {

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
        BasePiece bishop = new Bishop(Colour.BLUE);
        assertNotEquals(0, bishop.directions.length);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/legalBishopMoves.csv")
     void isLegalMove_validMoves_True(String start, String end) {
        Position startPosition = Position.valueOf(start);
        Position endPosition = Position.valueOf(end);

        BasePiece bishop = new Bishop(startPosition.getColour());
        boardMap.put(startPosition, bishop);

        assertTrue(bishop.isLegalMove(boardMap, startPosition, endPosition));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/illegalBishopMoves.csv")
     void isLegalMove_invalidMoves_False(String start, String end) {
        Position startPosition = Position.valueOf(start);
        Position endPosition = Position.valueOf(end);

        BasePiece bishop = new Bishop(startPosition.getColour());
        boardMap.put(startPosition, bishop);

        assertFalse(bishop.isLegalMove(boardMap, startPosition, endPosition));
    }

    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"BC1", "BF1", "RC1", "RF1", "GC1", "GF1"})
     void check_bishopPresentInInitialPosition_True(Position position) {
        BasePiece piece = boardMap.get(position);
        assertInstanceOf(Bishop.class, piece);
    }


    @ParameterizedTest
    @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_bishopTakesItsColourPiece_False(BasePiece piece) {
        BasePiece bishop = new Bishop(piece.colour);

        boardMap.put(BE4, bishop);
        boardMap.put(BD3, piece);

        assertFalse(bishop.isLegalMove(boardMap, BE4, BD3));
    }

    @ParameterizedTest
    @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_bishopTakesDifferentColourPiece_True(BasePiece piece) {
        BasePiece bishop = new Bishop(piece.colour.next());
        boardMap.put(BE4, bishop);

        boardMap.put(BD3, piece);
        assertTrue(bishop.isLegalMove(boardMap, BE4, BD3));
    }

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

    @ParameterizedTest
    @EnumSource(Colour.class)
     void toString_initBishopAllColours_correctStringFormat(Colour colour) {
        BasePiece bishop = new Bishop(colour);
        String expectedFormat = colour.toString() + "B";

        assertEquals(expectedFormat, bishop.toString());
    }
}
