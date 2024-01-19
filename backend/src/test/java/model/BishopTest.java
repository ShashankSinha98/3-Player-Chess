package model;

import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BishopTest {

    private Board board;

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    // Naming Convention- MethodName_StateUnderTest_ExpectedBehavior
    @Test
    public void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece bishop = new Bishop(Colour.BLUE);
        assertFalse(bishop.directions.length==0);
    }

    @Test
    public void isLegalMove_validMoves_True() {
        Position[] startPositions = new Position[] {BE4, BD3, GH3, RD3};
        Position[][] endPositions = new Position[][] {{BF3, BD3, GE4, RE4, RC4}, {RA2, GH3}, {BE2, BC4}, {BH3, GA2}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece bishop = new Bishop(start.getColour());
            board.boardMap.put(start, bishop);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertTrue(bishop.isLegalMove(board, start, end));
            }
        }
    }

    @Test
    public void isLegalMove_invalidMoves_False() {
        Position[] startPositions = new Position[] {BE4, BD3, GH3, RD3};
        Position[][] endPositions = new Position[][] {{BF4, BD4, GD4, RD4, RB4}, {BE3, BC3}, {GG3, GH4}, {RD4, RD2}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece bishop = new Bishop(start.getColour());
            board.boardMap.put(start, bishop);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertFalse(bishop.isLegalMove(board, start, end));
            }
        }
    }

    @Test
    public void isLegalMove_bishopAbsentFromStartPosition_False() {
        BasePiece bishop = new Bishop(Colour.BLUE);
        assertFalse(bishop.isLegalMove(board, BE4, BD3));
    }

    @Test
    public void isLegalMove_bishopTakesItsColourPiece_False() {
        BasePiece blueBishop = new Bishop(Colour.BLUE);
        board.boardMap.put(BE4, blueBishop);

        BasePiece bluePawn = new Pawn(Colour.BLUE);
        board.boardMap.put(BD3, bluePawn);

        assertFalse(blueBishop.isLegalMove(board, BE4, BD3));
    }

    @Test
    public void isLegalMove_bishopTakesDifferentColourPiece_True() {
        BasePiece blueBishop = new Bishop(Colour.BLUE);
        board.boardMap.put(BE4, blueBishop);

        BasePiece bluePawn = new Pawn(Colour.RED);
        board.boardMap.put(BD3, bluePawn);

        assertTrue(blueBishop.isLegalMove(board, BE4, BD3));
    }

    @Test
    public void getHighlightPolygons_validPolygons_presentInPolygonList() {
        Position[] startPositions = new Position[] {BE4, BD3, GH3, RD3};
        Position[][] endPositions = new Position[][] {{BF3, BD3, GE4, RE4, RC4}, {RA2, GH3}, {BE2, BC4}, {BH3, GA2}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece bishop = new Bishop(start.getColour());
            board.boardMap.put(start, bishop);
            Position[] ends = endPositions[i];

            List<Position> highlightedPolygons = bishop.getHighlightPolygons(board, start);
            for(Position end: ends) {
                assertTrue(highlightedPolygons.contains(end));
            }
        }
    }

    @Test
    public void getHighlightPolygons_invalidPolygons_absentInPolygonList() {
        Position[] startPositions = new Position[] {BE4, BD3, GH3, RD3};
        Position[][] endPositions = new Position[][] {{BF4, BD4, GD4, RD4, RB4}, {BE3, BC3}, {GG3, GH4}, {RD4, RD2}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece bishop = new Bishop(start.getColour());
            board.boardMap.put(start, bishop);
            Position[] ends = endPositions[i];

            List<Position> highlightedPolygons = bishop.getHighlightPolygons(board, start);
            for(Position end: ends) {
                assertFalse(highlightedPolygons.contains(end));
            }
        }
    }
}
