package model;

import common.Colour;
import common.Position;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    // Naming Convention- MethodName_StateUnderTest_ExpectedBehavior
    @Test
    void setupDirections_initPieceDirectionsIsEmpty_False(){
        BasePiece knight = new Knight(Colour.BLUE);
        assertNotEquals(0, knight.directions.length);
    }

    @Test
    void isLegalMove_knightTakesItsColourPiece_False() {
        Board board = new Board();
        board.boardMap.clear();

        BasePiece blueKnight = new Knight(Colour.BLUE);
        board.boardMap.put(BE2, blueKnight);

        BasePiece bluePawn = new Pawn(Colour.BLUE);
        board.boardMap.put(BF4, bluePawn);

        assertFalse(blueKnight.isLegalMove(board, BE4, BD3));
    }

    @Test
    void isLegalMove_knightTakesDifferentColourPiece_True() {
        Board board = new Board();
        board.boardMap.clear();

        BasePiece blueKnight = new Knight(Colour.BLUE);
        board.boardMap.put(BE2, blueKnight);

        BasePiece redPawn = new Pawn(Colour.RED);
        board.boardMap.put(BF4, redPawn);

        assertTrue(blueKnight.isLegalMove(board, BE2, BF4));
    }

    /** Tests if a knight can move over 3 pawns in front
     *          ...|x| |x|...
     *          ...|P|P|P|...
     *          ...| |N| |...
     */
    @Test
    void isLegalMove_knightMovesOverOwnColourPieces1_True(){
        Board board = new Board();
        board.boardMap.clear();

        BasePiece blueKnight = new Knight(Colour.BLUE);
        board.boardMap.put(BE2, blueKnight);

        BasePiece pawn1 = new Pawn(Colour.BLUE);
        BasePiece pawn2 = new Pawn(Colour.BLUE);
        BasePiece pawn3 = new Pawn(Colour.BLUE);

        board.boardMap.put(BD3, pawn1);
        board.boardMap.put(BE3, pawn2);
        board.boardMap.put(BF3, pawn3);

        assertTrue(blueKnight.isLegalMove(board, BE2, BD4));
        assertTrue(blueKnight.isLegalMove(board, BE2, BF4));
    }


    /** Tests if a knight can move over 2 pawns in front
     *          ...|x|P|x|...
     *          ...| |P| |...
     *          ...| |N| |...
     */
    @Test
    void isLegalMove_knightMovesOverOwnColourPieces2_True(){
        Board board = new Board();
        board.boardMap.clear();

        BasePiece blueKnight = new Knight(Colour.BLUE);
        board.boardMap.put(BE2, blueKnight);

        BasePiece pawn1 = new Pawn(Colour.BLUE);
        BasePiece pawn2 = new Pawn(Colour.BLUE);

        board.boardMap.put(BE3, pawn1);
        board.boardMap.put(BE4, pawn2);

        assertTrue(blueKnight.isLegalMove(board, BE2, BD4));
        assertTrue(blueKnight.isLegalMove(board, BE2, BF4));
    }

    @Test
    void getHighlightPolygons_validPolygons_presentInPolygonList1(){
        Board board = new Board();
        board.boardMap.clear();
        Position startPosition = BE4;

        BasePiece knight = new Knight(Colour.BLUE);
        board.boardMap.put(startPosition, knight);

        Set<Position> expectedKnightMoves = new HashSet<>(Arrays.asList(BG3, BF2, BD2, BC3, GF4, GE3, RB4, RC3, RF4, RE3));
        Set<Position> actualKnightMoves = new HashSet<>(knight.getHighlightPolygons(board, startPosition));

        assertEquals(expectedKnightMoves, actualKnightMoves);
    }

    @Test
    void getHighlightPolygons_validPolygons_presentInPolygonList2(){
        Board board = new Board();
        board.boardMap.clear();
        Position startPosition = BE4;

        BasePiece pawn = new Pawn(Colour.BLUE);
        board.boardMap.put(RB4, pawn);

        BasePiece knight = new Knight(Colour.BLUE);
        board.boardMap.put(startPosition, knight);

        Set<Position> expectedKnightMoves = new HashSet<>(Arrays.asList(BG3, BF2, BD2, BC3, GF4, GE3, RC3, RF4, RE3));
        Set<Position> actualKnightMoves = new HashSet<>(knight.getHighlightPolygons(board, startPosition));

        assertEquals(expectedKnightMoves, actualKnightMoves);
    }

    @Test
    void getHighlightPolygons_validPolygons_presentInPolygonList3(){
        Board board = new Board();
        board.boardMap.clear();
        Position startPosition = BE4;

        BasePiece pawn = new Pawn(Colour.RED);
        board.boardMap.put(RB4, pawn);

        BasePiece knight = new Knight(Colour.BLUE);
        board.boardMap.put(startPosition, knight);

        Set<Position> expectedKnightMoves = new HashSet<>(Arrays.asList(BG3, BF2, BD2, BC3, GF4, GE3, RB4, RC3, RF4, RE3));
        Set<Position> actualKnightMoves = new HashSet<>(knight.getHighlightPolygons(board, startPosition));

        assertEquals(expectedKnightMoves, actualKnightMoves);
    }

    @Test
    void toString_initBishopAllColours_correctStringFormat() {
        BasePiece blueKnight = new Knight(Colour.BLUE);
        assertEquals(blueKnight.toString(), "BN");

        BasePiece redKnight = new Knight(Colour.RED);
        assertEquals(redKnight.toString(), "RN");

        BasePiece greenKnight = new Knight(Colour.GREEN);
        assertEquals(greenKnight.toString(), "GN");
    }
}