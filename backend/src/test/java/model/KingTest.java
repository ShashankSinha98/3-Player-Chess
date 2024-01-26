package model;

import com.google.common.collect.ImmutableSet;
import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class KingTest {
    private Board board;

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece king = new King(Colour.GREEN);
        assertNotEquals(0, king.directions.length);
    }

    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"BE1", "RE1", "GE1"})
    void check_kingPresentInInitialPosition_True(Position position) {
        BasePiece piece = board.boardMap.get(position);
        assertInstanceOf(King.class, piece);
    }
    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"BD2", "RD2", "GD2"})
     void check_kingPresentInInitialPosition_False(Position position) {
        BasePiece piece = board.boardMap.get(position);
        assertFalse(piece instanceof King);
    }

     @ParameterizedTest
     @EnumSource(Colour.class)
     void isLegalMove_kingMovesToEmptySquare_True(Colour colour) {
         Board board = new Board();
         board.boardMap.clear();

         Position kingPosition = BE2;

         BasePiece king = new King(colour);
         board.boardMap.put(kingPosition, king);

         assertTrue(king.isLegalMove(board.boardMap, kingPosition, BE3));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_kingTakesItsColourPiece_False(BasePiece piece) {
         BasePiece king = new King(piece.colour);

         Position startPosition = BE4;
         Position endPosition = BD3;

         board.boardMap.put(startPosition, king);
         board.boardMap.put(endPosition, piece);

         assertFalse(king.isLegalMove(board.boardMap, startPosition, endPosition));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_kingTakesDifferentColourPiece_True(BasePiece piece) {
         BasePiece king = new King(piece.colour.next());

         Position startPosition = BE4;
         Position endPosition = BD3;

         board.boardMap.put(startPosition, king);
         board.boardMap.put(endPosition, piece);

         assertTrue(king.isLegalMove(board.boardMap, startPosition, endPosition));
     }
     @ParameterizedTest
     @EnumSource(Colour.class)
     void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
         Board board = new Board();
         board.boardMap.clear();                 //empty board
         Position startPosition = BE4;

         BasePiece king = new King(colour);
         board.boardMap.put(startPosition, king);

         Set<Position> expectedKingMoves =
                 ImmutableSet.of(GE4, BD3, RC4, BF3, BD4, BE3, RE4, BF4, RD4);
         Set<Position> actualKingMoves = king.getHighlightPolygons(board.boardMap, startPosition);

         assertEquals(expectedKingMoves, actualKingMoves);
     }

    @Test
    void isLegalMove_shortCastle_True() {
        Board board = new Board();
        board.boardMap.clear();

        Position kingPosition = RE1;
        Position rookPosition = RH1;

        BasePiece king = new King(kingPosition.getColour());
        BasePiece rook = new Rook(rookPosition.getColour());

        board.boardMap.put(kingPosition, king);
        board.boardMap.put(rookPosition, rook);

        assertTrue(king.isLegalMove(board.boardMap, kingPosition, RG1));
    }

     @Test
     void isLegalMove_shortCastleOccupiedSquare_False() {
         Board board = new Board();
         board.boardMap.clear();

         Position kingPosition = RE1;
         Position rookPosition = RH1;
         Position knightPosition = RG1;

         BasePiece king = new King(kingPosition.getColour());
         BasePiece rook = new Rook(rookPosition.getColour());
         BasePiece knight = new Knight(knightPosition.getColour());

         board.boardMap.put(kingPosition, king);
         board.boardMap.put(rookPosition, rook);
         board.boardMap.put(knightPosition, knight);

         assertFalse(king.isLegalMove(board.boardMap, kingPosition, RG1));
     }

     @Test
     void isLegalMove_longCastle_True() {
         Board board = new Board();
         board.boardMap.clear();

         Position kingPosition = RE1;
         Position rookPosition = RA1;

         BasePiece king = new King(kingPosition.getColour());
         BasePiece rook = new Rook(rookPosition.getColour());

         board.boardMap.put(kingPosition, king);
         board.boardMap.put(rookPosition, rook);

         assertTrue(king.isLegalMove(board.boardMap, kingPosition, RC1));
     }

     @Test
     void isLegalMove_longCastleOccupiedSquare_False() {
         Board board = new Board();
         board.boardMap.clear();

         Position kingPosition = RE1;
         Position rookPosition = RA1;
         Position knightPosition = RC1;

         BasePiece king = new King(kingPosition.getColour());
         BasePiece rook = new Rook(rookPosition.getColour());
         BasePiece knight = new Knight(knightPosition.getColour());

         board.boardMap.put(kingPosition, king);
         board.boardMap.put(rookPosition, rook);
         board.boardMap.put(knightPosition, knight);

         assertFalse(king.isLegalMove(board.boardMap, kingPosition, RC1));
     }


    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initKingAllColours_correctStringFormat(Colour colour) {
        BasePiece king = new King(colour);
        String expectedFormat = colour.toString() + "K";

        assertEquals(expectedFormat, king.toString());
    }
}
