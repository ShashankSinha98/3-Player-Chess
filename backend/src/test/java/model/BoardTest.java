package model;

import common.Colour;
import common.InvalidMoveException;
import common.InvalidPositionException;
import common.Position;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

 class BoardTest {

    private Board board;
    private Map<Position, BasePiece> boardMap;

    @BeforeEach
    void initBeforeEachBoardTest() {
       board = new Board();
       boardMap = board.boardMap;
    }

    // Naming Convention- MethodName_StateUnderTest_ExpectedBehavior
    @Test
     void move_pieceMoveToEmptyPolygon_startPositionEmptyAndEndPositionOccupied() throws InvalidPositionException, InvalidMoveException {
        BasePiece pawn = boardMap.get(BE2);
        board.move(BE2, BE4);
        assertNull(boardMap.get(BE2));
        assertNotNull(boardMap.get(BE4));
        assertEquals(pawn, boardMap.get(BE4));
    }

    @Test
     void move_wallMoveToEmptyPolygon_startPositionEmptyAndEndPositionOccupied() throws InvalidPositionException, InvalidMoveException {
        BasePiece wall = boardMap.get(BH2);
        assertInstanceOf(Wall.class, wall);
        assertEquals(Colour.BLUE, wall.getColour());

        board.move(BH2, BH4);
        assertNull(boardMap.get(BH2));
        assertNotNull(boardMap.get(BH4));
        assertEquals(wall, boardMap.get(BH4));
    }

    @Test
     void move_pawnToOppositeEndRow_pawnUpgradeToQueen() throws InvalidPositionException, InvalidMoveException {
        BasePiece bluePawn = new Pawn(Colour.BLUE);
        boardMap.put(RA2, bluePawn);
        boardMap.remove(RA1); // empty RA1 for blue pawn to move
        board.move(RA2, RA1);

        BasePiece promotedPiece = boardMap.get(RA1);
        assertInstanceOf(Queen.class, promotedPiece);
    }

    @Test
     void move_rightCastlingLegalMove_castlingHappen() throws InvalidPositionException, InvalidMoveException {
        boardMap.remove(BF1);
        boardMap.remove(BG1);

        BasePiece king = boardMap.get(BE1);
        BasePiece rightRook = boardMap.get(BH1);

        board.move(BE1, BG1); // left castling
        assertEquals(king, boardMap.get(BG1));
        assertEquals(rightRook, boardMap.get(BF1));
    }

    @Test
     void move_leftCastlingLegalMove_castlingHappen() throws InvalidPositionException, InvalidMoveException {
        boardMap.remove(BD1);
        boardMap.remove(BC1);
        boardMap.remove(BB1);

        BasePiece king = boardMap.get(BE1);
        BasePiece leftRook = boardMap.get(BA1);

        board.move(BE1, BC1); // left castling
        assertEquals(king, boardMap.get(BC1));
        assertEquals(leftRook, boardMap.get(BD1));
    }

    @Test
     void getPossibleMoves_emptyPosition_emptyPositionsList() {
        Set<Position> possibleMoves = board.getPossibleMoves(BE4);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
     void getPossibleMoves_rookBehindJesterInitialPosition_emptyPositionsList() {
        Set<Position> possibleMoves = board.getPossibleMoves(BA1);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
     void getPossibleMoves_rookBehindWallInitialPosition_nonEmptyPositionsList() {
        Set<Position> possibleMoves = board.getPossibleMoves(BH1);
        assertFalse(possibleMoves.isEmpty());
    }
}
