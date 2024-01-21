package model;

import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WallTest {
    private Board board;

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    @Test
    public void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece wall = new Wall(Colour.BLUE);
        assertNotEquals(0, wall.directions.length);
    }

    @Test
    public void canMove_validMoves_True() {
        Position[] startPositions = new Position[] {BH2, BE4, GE4};
        Position[][] endPositions = new Position[][] {{BH3, BH4, RA4, RA3}, {BD4, BE3, BG4, BF4, BH4}, {GB4, GH4, BD3}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece wall = new Wall(start.getColour());
            board.boardMap.put(start, wall);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertTrue(wall.canMove(board, start, end));
            }
        }
    }

    @Test
    public void canMove_invalidMoves_False() {
        Position[] startPositions = new Position[] {GE4, BB3};
        Position[][] endPositions = new Position[][] {{GF3, BA3, BH2}, {BF2, BA1, GH4, GF4}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece wall = new Wall(start.getColour());
            board.boardMap.put(start, wall);
            Position[] ends = endPositions[i];
            for(Position end: ends) {
                assertFalse(wall.canMove(board, start, end));
            }
        }
    }

    @Test
    public void isLegalMove_wallPresentInInitialPosition_True() {
        Position[] wallInitialPositions = new Position[] {BH2, RH2, GH2};
        for(Position position: wallInitialPositions) {
            BasePiece piece = board.boardMap.get(position);
            assertInstanceOf(Wall.class, piece);
        }
    }
    @Test
    public void isLegalMove_wallPresentInInitialPosition_False() {
        Position[] wallInitialPositions = new Position[] {BA2, RA2, GA2};
        for(Position position: wallInitialPositions) {
            BasePiece piece = board.boardMap.get(position);
            assertFalse(piece instanceof  Wall);
        }
    }

    @Test
    public void getHighlightPolygons_validPolygons_presentInPolygonList() {
        Position[] startPositions = new Position[] {BA4, BF4, GD4};
        Position[][] endPositions = new Position[][] {{BA3, GH4, GH3, BC4, BG4}, {BF3, BC4, RC3, BH4}, {RE3, GC4, GH4, GD3, GA4}};

        for(int i=0; i<startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece wall = new Wall(start.getColour());
            board.boardMap.put(start, wall);
            Position[] ends = endPositions[i];

            List<Position> highlightedPolygons = wall.getHighlightPolygons(board, start);
            for(Position end: ends) {
                assertTrue(highlightedPolygons.contains(end));
            }
        }
    }

    @Test
    public void getHighlightPolygons_invalidPolygons_absentInPolygonList() {
        Position[] startPositions = new Position[] {RD3, BG3, RE1};
        Position[][] endPositions = new Position[][] {{GD1, BF3, BA1}, {RA3, BG3, GH4, GC2}, {RA3, BC2, GA4, RD3}};

        for (int i = 0; i < startPositions.length; i++) {
            Board board = new Board();
            Position start = startPositions[i];
            BasePiece wall = new Wall(start.getColour());
            board.boardMap.put(start, wall);
            Position[] ends = endPositions[i];

            List<Position> highlightedPolygons = wall.getHighlightPolygons(board, start);
            for (Position end : ends) {
                assertFalse(highlightedPolygons.contains(end));
            }
        }
    }

    @Test
    public void toString_initWallAllColours_correctStringFormat() {
        BasePiece blueWall = new Wall(Colour.BLUE);
        assertEquals(blueWall.toString(), "BW");

        BasePiece redWall = new Wall(Colour.RED);
        assertEquals(redWall.toString(), "RW");

        BasePiece greenWall = new Wall(Colour.GREEN);
        assertEquals(greenWall.toString(), "GW");
    }
}
