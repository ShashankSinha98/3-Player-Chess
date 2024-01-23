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

public class QueenTest {

    private Board board;

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    @Test
    public void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece queen = new Queen(Colour.BLUE);
        assertNotEquals(0, queen.directions.length);
    }

    @Test
    public void isLegalMove_validMoves_True() {
        Position[] startPositions = new Position[] {BE4, BD3, GH3, RD3};
        Position[][] endPositions = new Position[][] {{BF3, BD3, GE4, RE4, RC4, BE3, BD4, BF4, RD4}, {RA2, GH3}, {BE2, BC4}, {BH3, GA2}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece queen = new Queen(start.getColour());
            board.boardMap.put(start, queen);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertTrue(queen.isLegalMove(board, start, end));
            }
        }
    }

    @Test
    public void isLegalMove_invalidMoves_False() {
        Position[] startPositions = new Position[] {BF3, BE4};
        Position[][] endPositions = new Position[][] {{BH4, RB4}, {BG3, BH3, GH4, GF4}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece queen = new Queen(start.getColour());
            board.boardMap.put(start, queen);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertFalse(queen.isLegalMove(board, start, end));
            }
        }
    }

    @Test
    public void isLegalMove_queenPresentInInitialPosition_True() {
        Position[] queenInitialPositions = new Position[] {BD1, RD1, GD1};
        for(Position position: queenInitialPositions) {
            BasePiece piece = board.boardMap.get(position);
            assertInstanceOf(Queen.class, piece);
        }
    }

    @Test
    public void getHighlightPolygons_validPolygons_presentInPolygonList() {
        Position[] startPositions = new Position[] {BE4, BD3, GH3, RD3};
        Position[][] endPositions = new Position[][] {{BF3, BD3, GE4, RE4, RC4, BE3, BD4, BF4, RD4}, {RA2, GH3}, {BE2, BC4}, {BH3, GA2}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece queen = new Queen(start.getColour());
            board.boardMap.put(start, queen);
            Position[] ends = endPositions[i];

            Set<Position> highlightedPolygons = queen.getHighlightPolygons(board, start);
            for(Position end: ends) {
                assertTrue(highlightedPolygons.contains(end));
            }
        }
    }

    @Test
    public void getHighlightPolygons_invalidPolygons_absentInPolygonList() {
        Position[] startPositions = new Position[] {BF3, BE4};
        Position[][] endPositions = new Position[][] {{BH4, RB4}, {BG3, BH3, GH4, GF4}};

        for (int i = 0; i < startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece queen = new Queen(start.getColour());
            board.boardMap.put(start, queen);
            Position[] ends = endPositions[i];

            Set<Position> highlightedPolygons = queen.getHighlightPolygons(board, start);
            for (Position end : ends) {
                assertFalse(highlightedPolygons.contains(end));
            }
        }
    }

    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initQueenAllColours_correctStringFormat(Colour colour) {
        BasePiece queen = new Queen(colour);
        String expectedFormat = colour.toString() + "Q";

        assertEquals(expectedFormat, queen.toString());
    }
}
