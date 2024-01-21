package model;

import common.Colour;
import common.Position;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

class JesterTest {

    @Test
    void setupDirections_initPieceDirectionsIsEmpty_False(){
        BasePiece jester = new Jester(Colour.BLUE);
        assertNotEquals(0, jester.directions.length);
    }

    @Test
    void isLegalMove_jesterMovesToEmptySquare_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        assertTrue(jester.isLegalMove(board, jesterPosition, BF4));
    }

    @Test
    void isLegalMove_jesterTakesSameColourPawn_False() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position pawnPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece pawn = new Pawn(Colour.BLUE);
        board.boardMap.put(pawnPosition, pawn);

        assertFalse(jester.isLegalMove(board, jesterPosition, pawnPosition));
    }

    @Test
    void isLegalMove_jesterTakesSameColourRook_False() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position rookPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece rook = new Rook(Colour.BLUE);
        board.boardMap.put(rookPosition, rook);

        assertFalse(jester.isLegalMove(board, jesterPosition, rookPosition));
    }

    @Test
    void isLegalMove_jesterTakesSameColourKnight_False() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position knightPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece knight = new Knight(Colour.BLUE);
        board.boardMap.put(knightPosition, knight);

        assertFalse(jester.isLegalMove(board, jesterPosition, knightPosition));
    }

    @Test
    void isLegalMove_jesterTakesSameColourBishop_False() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position bishopPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece bishop = new Bishop(Colour.BLUE);
        board.boardMap.put(bishopPosition, bishop);

        assertFalse(jester.isLegalMove(board, jesterPosition, bishopPosition));
    }

    @Test
    void isLegalMove_jesterTakesSameColourKing_False() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position kingPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece king = new King(Colour.BLUE);
        board.boardMap.put(kingPosition, king);

        assertFalse(jester.isLegalMove(board, jesterPosition, kingPosition));
    }

    @Test
    void isLegalMove_jesterTakesSameColourQueen_False() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position queenPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece queen = new Queen(Colour.BLUE);
        board.boardMap.put(queenPosition, queen);

        assertFalse(jester.isLegalMove(board, jesterPosition, queenPosition));
    }

    @Test
    void isLegalMove_jesterTakesSameColourWall_False() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position wallPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece wall = new Wall(Colour.BLUE);
        board.boardMap.put(wallPosition, wall);

        assertFalse(jester.isLegalMove(board, jesterPosition, wallPosition));
    }

    @Test
    void isLegalMove_jesterTakesDifferentColourPawn_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position pawnPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece pawn = new Pawn(Colour.RED);
        board.boardMap.put(pawnPosition, pawn);

        assertTrue(jester.isLegalMove(board, jesterPosition, pawnPosition));
    }

    @Test
    void isLegalMove_jesterTakesDifferentColourRook_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position rookPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece rook = new Rook(Colour.RED);
        board.boardMap.put(rookPosition, rook);

        assertTrue(jester.isLegalMove(board, jesterPosition, rookPosition));
    }

    @Test
    void isLegalMove_jesterTakesDifferentColourKnight_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position knightPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece knight = new Knight(Colour.RED);
        board.boardMap.put(knightPosition, knight);

        assertTrue(jester.isLegalMove(board, jesterPosition, knightPosition));
    }

    @Test
    void isLegalMove_jesterTakesDifferentColourBishop_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position bishopPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece bishop = new Bishop(Colour.RED);
        board.boardMap.put(bishopPosition, bishop);

        assertTrue(jester.isLegalMove(board, jesterPosition, bishopPosition));
    }

    @Test
    void isLegalMove_jesterTakesDifferentColourWall_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position wallPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece wall = new Wall(Colour.RED);
        board.boardMap.put(wallPosition, wall);

        assertTrue(jester.isLegalMove(board, jesterPosition, wallPosition));
    }

    @Test
    void isLegalMove_jesterTakesDifferentColourKing_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position kingPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece king = new King(Colour.RED);
        board.boardMap.put(kingPosition, king);

        assertTrue(jester.isLegalMove(board, jesterPosition, kingPosition));
    }

    @Test
    void isLegalMove_jesterTakesDifferentColourQueen_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition = BE2;
        Position queenPosition = BF4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition, jester);

        BasePiece queen = new Queen(Colour.RED);
        board.boardMap.put(queenPosition, queen);

        assertTrue(jester.isLegalMove(board, jesterPosition, queenPosition));
    }

    @Test
    void isLegalMove_jesterTakesDifferentColourJester_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position jesterPosition1 = BE2;
        Position jesterPosition2 = BF4;

        BasePiece jester1 = new Jester(Colour.BLUE);
        board.boardMap.put(jesterPosition1, jester1);

        BasePiece jester2 = new Jester(Colour.RED);
        board.boardMap.put(jesterPosition2, jester2);

        assertTrue(jester1.isLegalMove(board, jesterPosition1, jesterPosition2));
    }

    @Test
    void getHighlightPolygons_validPolygons_presentInPolygonList1(){
        Board board = new Board();
        board.boardMap.clear();
        Position startPosition = BE4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(startPosition, jester);

        Set<Position> expectedJesterMoves = new HashSet<>(Arrays.asList(BG3, BF2, BD2, BC3, GF4, GE3, RB4, RC3, RF4, RE3));
        Set<Position> actualJesterMoves = new HashSet<>(jester.getHighlightPolygons(board, startPosition));

        assertEquals(expectedJesterMoves, actualJesterMoves);
    }

    @Test
    void getHighlightPolygons_validPolygons_presentInPolygonList2(){
        Board board = new Board();
        board.boardMap.clear();
        Position startPosition = BE4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(startPosition, jester);

        BasePiece pawn = new Pawn(Colour.BLUE);
        board.boardMap.put(RB4, pawn);

        Set<Position> expectedJesterMoves = new HashSet<>(Arrays.asList(BG3, BF2, BD2, BC3, GF4, GE3, RC3, RF4, RE3));
        Set<Position> actualJesterMoves = new HashSet<>(jester.getHighlightPolygons(board, startPosition));

        assertEquals(expectedJesterMoves, actualJesterMoves);
    }

    @Test
    void getHighlightPolygons_validPolygons_presentInPolygonList3(){
        Board board = new Board();
        board.boardMap.clear();
        Position startPosition = BE4;

        BasePiece jester = new Jester(Colour.BLUE);
        board.boardMap.put(startPosition, jester);

        BasePiece pawn = new Pawn(Colour.RED);
        board.boardMap.put(RB4, pawn);

        Set<Position> expectedJesterMoves = new HashSet<>(Arrays.asList(BG3, BF2, BD2, BC3, GF4, GE3, RB4, RC3, RF4, RE3));
        Set<Position> actualJesterMoves = new HashSet<>(jester.getHighlightPolygons(board, startPosition));

        assertEquals(expectedJesterMoves, actualJesterMoves);
    }


    @Test
    void toString_initJesterAllColours_correctStringFormat() {
        BasePiece blueJester = new Jester(Colour.BLUE);
        assertEquals(blueJester.toString(), "BJ");

        BasePiece redJester = new Jester(Colour.RED);
        assertEquals(redJester.toString(), "RJ");

        BasePiece greenJester = new Jester(Colour.GREEN);
        assertEquals(greenJester.toString(), "GJ");
    }
}