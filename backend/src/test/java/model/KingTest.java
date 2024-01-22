package model;

import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KingTest {
    private Board board;

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    @Test
    public void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece king = new King(Colour.GREEN);
        assertNotEquals(0, king.directions.length);
    }

    @Test
    public void canMove_validMoves_True() {
        Position[] startPositions = new Position[] {RE1, BC3, GH4};
        Position[][] endPositions = new Position[][] {{RE2, RD1, RD2, RF1, RF2},
                {BC2, BC4, BD2, BD3, BB4, BB2}, {BA4, GH3, BB4, GG3, GG4}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            board.boardMap.clear();
            Position start = startPositions[i];
            BasePiece king = new King(start.getColour());
            board.boardMap.put(start, king);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertTrue(king.canMove(board, start, end));
            }
        }
    }

    @Test
    public void canMove_invalidMoves_False() {
        Position[] startPositions = new Position[] {RE2, BA1, GF4};
        Position[][] endPositions = new Position[][] {{RE2, RD4, GF1, BA3, BE4},
                {BA3, BB3, BC3, GF4}, {BA4, GH3, GD3, RD4}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece king = new King(start.getColour());
            board.boardMap.put(start, king);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertFalse(king.canMove(board, start, end));
            }
        }
    }

    @Test
    public void isLegalMove_kingPresentInInitialPosition_True() {
        Position[] kingInitialPositions = new Position[] {BE1, RE1, GE1};
        for(Position position: kingInitialPositions) {
            BasePiece piece = board.boardMap.get(position);
            assertInstanceOf(King.class, piece);
        }
    }
    @Test
    public void isLegalMove_kingPresentInInitialPosition_False() {
        Position[] kingInitialPositions = new Position[] {BD2, RD2, GD2};
        for(Position position: kingInitialPositions) {
            BasePiece piece = board.boardMap.get(position);
            assertFalse(piece instanceof  King);
        }
    }

    @Test
    public void getHighlightPolygons_validPolygons_presentInPolygonList() {
        Position[] startPositions = new Position[] {RE1, BC3, GH4};
        Position[][] endPositions = new Position[][] {{RE2, RD1, RD2, RF1, RF2},
                {BC2, BC4, BD2, BD3, BB4, BB2}, {BA4, GH3, BB4, GG3, GG4}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            board.boardMap.clear();
            Position start = startPositions[i];
            BasePiece king = new King(start.getColour());
            board.boardMap.put(start, king);
            Position[] ends = endPositions[i];

            List<Position> highlightedPolygons = king.getHighlightPolygons(board, start);
            for(Position end: ends) {
                assertTrue(highlightedPolygons.contains(end));
            }
        }
    }

    @Test
    public void getHighlightPolygons_invalidPolygons_absentInPolygonList() {
        Position[] startPositions = new Position[] {RE2, BA1, GF4};
        Position[][] endPositions = new Position[][] {{RE2, RD4, GF1, BA3, BE4},
                {BA3, BB3, BC3, GF4}, {BA4, GH3, GD3, RD4}};

        for (int i = 0; i < startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece king = new King(start.getColour());
            board.boardMap.put(start, king);
            Position[] ends = endPositions[i];

            List<Position> highlightedPolygons = king.getHighlightPolygons(board, start);
            for (Position end : ends) {
                assertFalse(highlightedPolygons.contains(end));
            }
        }
    }
    @Test
    public void isCastlingPossible_True() {
        Position[] startPositions = new Position[] {RE1, BE1, GE1};
        Position[][] endPositions = new Position[][] {{RC1, RG1},
                {BC1, BG1}, {GC1, GG1}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            board.boardMap.clear();
            Position start = startPositions[i];
            BasePiece king = new King(start.getColour());
            board.boardMap.put(start, king);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                board.boardMap.put(end, new Rook(start.getColour()));
            }
            for(Position end: ends) {
                assertTrue(king.canMove(board, start, end));
            }
        }
    }
    @Test
    public void isCastlingPossible_False() {
        Position[] startPositions = new Position[] {RE1, BE1, GE1};
        Position[][] endPositions = new Position[][] {{RC2, RG2},
                {BC2, BG2}, {GC2, GG2}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece king = new King(start.getColour());
            board.boardMap.put(start, king);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                board.boardMap.put(end, new Rook(start.getColour()));
            }
            for(Position end: ends) {
                assertFalse(king.canMove(board, start, end));
            }
        }
    }
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initKingAllColours_correctStringFormat(Colour colour) {
        BasePiece king = new King(colour);
        String expectedFormat = colour.toString() + "K";

        assertEquals(expectedFormat, king.toString());
    }
}
