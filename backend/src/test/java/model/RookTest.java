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

public class RookTest {
    private Board board;

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    @Test
    public void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece rook = new Rook(Colour.BLUE);
        assertNotEquals(0, rook.directions.length);
    }

    @Test
    public void isLegalMove_validMoves_True() {
        Position[] startPositions = new Position[] {BE4, BA3, GD4};
        Position[][] endPositions = new Position[][] {{BE3, BD4, BF4, RD4}, {BH3, GH3}, {GA4, GH4, RE3}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece rook = new Rook(start.getColour());
            board.boardMap.put(start, rook);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertTrue(rook.isLegalMove(board, start, end));
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
            BasePiece rook = new Rook(start.getColour());
            board.boardMap.put(start, rook);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertFalse(rook.isLegalMove(board, start, end));
            }
        }
    }

    @Test
    public void isLegalMove_rookPresentInInitialPosition_True() {
        Position[] rookInitialPositions = new Position[] {BA1, BH1, RA1, RH1, GA1, GH1};
        for(Position position: rookInitialPositions) {
            BasePiece piece = board.boardMap.get(position);
            assertInstanceOf(Rook.class, piece);
        }
    }

    @Test
    public void getHighlightPolygons_validPolygons_presentInPolygonList() {
        Position[] startPositions = new Position[] {BE4, BA3, GD4};
        Position[][] endPositions = new Position[][] {{BE3, BD4, BF4, RD4}, {BH3, GH3}, {GA4, GH4, RE3}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece rook = new Rook(start.getColour());
            board.boardMap.put(start, rook);
            Position[] ends = endPositions[i];

            Set<Position> highlightedPolygons = rook.getHighlightPolygons(board, start);
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
            BasePiece rook = new Rook(start.getColour());
            board.boardMap.put(start, rook);
            Position[] ends = endPositions[i];

            Set<Position> highlightedPolygons = rook.getHighlightPolygons(board, start);
            for (Position end : ends) {
                assertFalse(highlightedPolygons.contains(end));
            }
        }
    }

    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initRookAllColours_correctStringFormat(Colour colour) {
        BasePiece rook = new Rook(colour);
        String expectedFormat = colour.toString() + "R";

        assertEquals(expectedFormat, rook.toString());
    }
}
