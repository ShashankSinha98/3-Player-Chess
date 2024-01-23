package model;

import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {

    private Board board;


    static Stream <BasePiece> pieceProvider() {
        //maybe replace with for loop
        return Stream.of(
                new Pawn(Colour.RED),
                new Pawn(Colour.BLUE),
                new Pawn(Colour.GREEN),
                new Rook(Colour.RED),
                new Rook(Colour.BLUE),
                new Rook(Colour.GREEN),
                new Knight(Colour.BLUE),
                new Knight(Colour.GREEN),
                new Knight(Colour.RED),
                new Bishop(Colour.BLUE),
                new Bishop(Colour.GREEN),
                new Bishop(Colour.RED),
                new Queen(Colour.BLUE),
                new Queen(Colour.GREEN),
                new Queen(Colour.RED),
                new King(Colour.BLUE),
                new King(Colour.GREEN),
                new King(Colour.RED),
                new Jester(Colour.BLUE),
                new Jester(Colour.GREEN),
                new Jester(Colour.RED),
                new Wall(Colour.BLUE),
                new Wall(Colour.GREEN),
                new Wall(Colour.RED)
        );
    }

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    // Naming Convention- MethodName_StateUnderTest_ExpectedBehavior
    @Test
    public void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece bishop = new Bishop(Colour.BLUE);
        assertNotEquals(0, bishop.directions.length);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/legalBishopMoves.csv")
    public void isLegalMove_validMoves_True(String start, String end) {
        Board board = new Board();
        Position startPosition = Position.valueOf(start);
        Position endPosition = Position.valueOf(end);

        BasePiece bishop = new Bishop(startPosition.getColour());
        board.boardMap.put(startPosition, bishop);

        assertTrue(bishop.isLegalMove(board, startPosition, endPosition));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/illegalBishopMoves.csv")
    public void isLegalMove_invalidMoves_False(String start, String end) {
        Board board = new Board();
        Position startPosition = Position.valueOf(start);
        Position endPosition = Position.valueOf(end);

        BasePiece bishop = new Bishop(startPosition.getColour());
        board.boardMap.put(startPosition, bishop);

        assertFalse(bishop.isLegalMove(board, startPosition, endPosition));
    }

    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"BC1", "BF1", "RC1", "RF1", "GC1", "GF1"})
    public void isLegalMove_bishopPresentInInitialPosition_True(Position position) {
        BasePiece piece = board.boardMap.get(position);
        assertInstanceOf(Bishop.class, piece);
    }


    @ParameterizedTest
    @MethodSource("pieceProvider")
    public void isLegalMove_bishopTakesItsColourPiece_False(BasePiece piece) {
        BasePiece bishop = new Bishop(piece.colour);

        board.boardMap.put(BE4, bishop);
        board.boardMap.put(BD3, piece);

        assertFalse(bishop.isLegalMove(board, BE4, BD3));
    }

    @ParameterizedTest
    @MethodSource("pieceProvider")
    public void isLegalMove_bishopTakesDifferentColourPiece_True(BasePiece piece) {
        BasePiece bishop = new Bishop(piece.colour.next());
        board.boardMap.put(BE4, bishop);

        board.boardMap.put(BD3, piece);

        assertTrue(bishop.isLegalMove(board, BE4, BD3));
    }

    @ParameterizedTest
    @EnumSource(Colour.class)
    public void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
        Board board = new Board();
        board.boardMap.clear();                 //empty board
        Position startPosition = BE4;

        BasePiece bishop = new Bishop(colour);
        board.boardMap.put(startPosition, bishop);

        Set<Position> expectedBishopMoves = new HashSet<>(
                Arrays.asList(BH1, BG2, BF3, BD3, BC2, BB1, RC4, RB3, RA2, GE4, GF3, GG2, GH1, RE4, RF3, RG2, RH1));
        Set<Position> actualBishopMoves = new HashSet<>(bishop.getHighlightPolygons(board, startPosition));

        assertEquals(expectedBishopMoves, actualBishopMoves);
    }

    @ParameterizedTest
    @EnumSource(Colour.class)
    public void toString_initBishopAllColours_correctStringFormat(Colour colour) {
        BasePiece bishop = new Bishop(colour);
        String expectedFormat = colour.toString() + "B";

        assertEquals(expectedFormat, bishop.toString());
    }
}
