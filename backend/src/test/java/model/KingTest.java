package model;

import com.google.common.collect.ImmutableSet;
import common.Colour;
import common.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class KingTest {

     private Board board;
     private Map<Position, BasePiece> boardMap;

     @BeforeEach
     void initBeforeEachBoardTest() {
         board = new Board();
         boardMap = board.boardMap;
     }

    @Test
     void setupDirections_initPieceDirectionsIsEmpty_False() {
        BasePiece king = new King(Colour.GREEN);
        assertNotEquals(0, king.directions.length);
    }

    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"BE1", "RE1", "GE1"})
    void check_kingPresentInInitialPosition_True(Position position) {
        BasePiece piece = boardMap.get(position);
        assertInstanceOf(King.class, piece);
    }
    @ParameterizedTest
    @EnumSource(value = Position.class, names = {"BD2", "RD2", "GD2"})
     void check_kingPresentInInitialPosition_False(Position position) {
        BasePiece piece = boardMap.get(position);
        assertFalse(piece instanceof King);
    }

     @ParameterizedTest
     @EnumSource(Colour.class)
     void isLegalMove_kingMovesToEmptySquare_True(Colour colour) {
         Board board = new Board();
         boardMap.clear();

         Position kingPosition = BE2;

         BasePiece king = new King(colour);
         boardMap.put(kingPosition, king);

         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
         assertTrue(actualKingMoves.contains(BE3));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_kingTakesItsColourPiece_False(BasePiece piece) {
         BasePiece king = new King(piece.colour);

         Position startPosition = BE4;
         Position endPosition = BD3;

         boardMap.put(startPosition, king);
         boardMap.put(endPosition, piece);

         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, startPosition);
         assertFalse(actualKingMoves.contains(endPosition));
     }

     @ParameterizedTest
     @MethodSource("model.DataProvider#pieceProvider")
     void isLegalMove_kingTakesDifferentColourPiece_True(BasePiece piece) {
         BasePiece king = new King(piece.colour.next());

         Position startPosition = BE4;
         Position endPosition = BD3;

         boardMap.put(startPosition, king);
         boardMap.put(endPosition, piece);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, startPosition);
         assertTrue(actualKingMoves.contains(endPosition));
     }
     @ParameterizedTest
     @EnumSource(Colour.class)
     void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
         Board board = new Board();
         boardMap.clear();                 //empty board
         Position startPosition = BE4;

         BasePiece king = new King(colour);
         boardMap.put(startPosition, king);

         Set<Position> expectedKingMoves =
                 ImmutableSet.of(GE4, BD3, RC4, BF3, BD4, BE3, RE4, BF4, RD4);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, startPosition);

         assertEquals(expectedKingMoves, actualKingMoves);
     }

    @Test
    void isLegalMove_shortCastle_True() {
        Board board = new Board();
        boardMap.clear();

        Position kingPosition = RE1;
        Position rookPosition = RH1;

        BasePiece king = new King(kingPosition.getColour());
        BasePiece rook = new Rook(rookPosition.getColour());

        boardMap.put(kingPosition, king);
        boardMap.put(rookPosition, rook);
        Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
        assertTrue(actualKingMoves.contains(RG1));
    }

     @Test
     void isLegalMove_shortCastleOccupiedSquare_False() {
         Board board = new Board();
         boardMap.clear();

         Position kingPosition = RE1;
         Position rookPosition = RH1;
         Position knightPosition = RG1;

         BasePiece king = new King(kingPosition.getColour());
         BasePiece rook = new Rook(rookPosition.getColour());
         BasePiece knight = new Knight(knightPosition.getColour());

         boardMap.put(kingPosition, king);
         boardMap.put(rookPosition, rook);
         boardMap.put(knightPosition, knight);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
         assertFalse(actualKingMoves.contains(RG1));
     }

     @Test
     void isLegalMove_longCastle_True() {
         Board board = new Board();
         boardMap.clear();

         Position kingPosition = RE1;
         Position rookPosition = RA1;

         BasePiece king = new King(kingPosition.getColour());
         BasePiece rook = new Rook(rookPosition.getColour());

         boardMap.put(kingPosition, king);
         boardMap.put(rookPosition, rook);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
         assertTrue(actualKingMoves.contains(RC1));
     }

     @Test
     void isLegalMove_longCastleOccupiedSquare_False() {
         Board board = new Board();
         boardMap.clear();

         Position kingPosition = RE1;
         Position rookPosition = RA1;
         Position knightPosition = RC1;

         BasePiece king = new King(kingPosition.getColour());
         BasePiece rook = new Rook(rookPosition.getColour());
         BasePiece knight = new Knight(knightPosition.getColour());

         boardMap.put(kingPosition, king);
         boardMap.put(rookPosition, rook);
         boardMap.put(knightPosition, knight);
         Set<Position> actualKingMoves = king.getHighlightPolygons(boardMap, kingPosition);
         assertFalse(actualKingMoves.contains(RC1));
     }


    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initKingAllColours_correctStringFormat(Colour colour) {
        BasePiece king = new King(colour);
        String expectedFormat = colour.toString() + "K";

        assertEquals(expectedFormat, king.toString());
    }
}
