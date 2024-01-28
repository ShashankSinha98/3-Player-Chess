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

/**
 * This class contains unit tests for the Board class.
 */
 class BoardTest {

    private Board board;
    private Map<Position, BasePiece> boardMap;

   /**
    * Initializes a new Board instance before each test.
    */
    @BeforeEach
    void initBeforeEachBoardTest() {
       board = new Board();
       boardMap = board.boardMap;
    }

   /**
    * Tests the move method for moving a piece to an empty polygon,
    * expecting the start position to be empty and the end position to be occupied by the piece.
    *
    * @throws InvalidPositionException If an invalid position is encountered.
    * @throws InvalidMoveException     If an invalid move is attempted.
    */
    @Test
     void move_pieceMoveToEmptyPolygon_startPositionEmptyAndEndPositionOccupied() throws InvalidPositionException, InvalidMoveException {
        BasePiece pawn = boardMap.get(BE2);
        board.move(BE2, BE4);
        assertNull(boardMap.get(BE2));
        assertNotNull(boardMap.get(BE4));
        assertEquals(pawn, boardMap.get(BE4));
    }

   /**
    * Tests the move method for moving a wall to an empty polygon,
    * expecting the start position to be empty and the end position to be occupied by the wall.
    *
    * @throws InvalidPositionException If an invalid position is encountered.
    * @throws InvalidMoveException     If an invalid move is attempted.
    */
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

   /**
    * Tests the move method for moving a pawn to the opposite end row,
    * expecting the pawn promoting to a queen.
    *
    * @throws InvalidPositionException If an invalid position is encountered.
    * @throws InvalidMoveException     If an invalid move is attempted.
    */
    @Test
     void move_pawnToOppositeEndRow_pawnUpgradeToQueen() throws InvalidPositionException, InvalidMoveException {
        BasePiece bluePawn = new Pawn(Colour.BLUE);
        boardMap.put(RA2, bluePawn);
        boardMap.remove(RA1); // empty RA1 for blue pawn to move
        board.move(RA2, RA1);

        BasePiece promotedPiece = boardMap.get(RA1);
        assertInstanceOf(Queen.class, promotedPiece);
    }

   /**
    * Tests the move method for short castling legal move,
    * expecting castling to occur.
    *
    * @throws InvalidPositionException If an invalid position is encountered.
    * @throws InvalidMoveException     If an invalid move is attempted.
    */
    @Test
     void move_shortCastlingLegalMove_castlingHappen() throws InvalidPositionException, InvalidMoveException {
        boardMap.remove(BF1);
        boardMap.remove(BG1);

        BasePiece king = boardMap.get(BE1);
        BasePiece rightRook = boardMap.get(BH1);

        board.move(BE1, BG1); // left castling
        assertEquals(king, boardMap.get(BG1));
        assertEquals(rightRook, boardMap.get(BF1));
    }

   /**
    * Tests the move method for long castling legal move,
    * expecting castling to occur.
    *
    * @throws InvalidPositionException If an invalid position is encountered.
    * @throws InvalidMoveException     If an invalid move is attempted.
    */
    @Test
     void move_longCastlingLegalMove_castlingHappen() throws InvalidPositionException, InvalidMoveException {
        boardMap.remove(BD1);
        boardMap.remove(BC1);
        boardMap.remove(BB1);

        BasePiece king = boardMap.get(BE1);
        BasePiece leftRook = boardMap.get(BA1);

        board.move(BE1, BC1); // left castling
        assertEquals(king, boardMap.get(BC1));
        assertEquals(leftRook, boardMap.get(BD1));
    }

   /**
    * Tests the getPossibleMoves method for an empty position,
    * expecting an empty positions list.
    */
    @Test
     void getPossibleMoves_emptyPosition_emptyPositionsList() {
        Set<Position> possibleMoves = board.getPossibleMoves(BE4);
        assertTrue(possibleMoves.isEmpty());
    }

   /**
    * Tests the getPossibleMoves method for a rook positioned behind a jester initially,
    * expecting an empty positions list.
    */
    @Test
     void getPossibleMoves_rookBehindJesterInitialPosition_emptyPositionsList() {
        Set<Position> possibleMoves = board.getPossibleMoves(BA1);
        assertTrue(possibleMoves.isEmpty());
    }

   /**
    * Tests the getPossibleMoves method for a rook positioned behind a wall initially,
    * expecting a non-empty positions list.
    */
    @Test
     void getPossibleMoves_rookBehindWallInitialPosition_nonEmptyPositionsList() {
        Set<Position> possibleMoves = board.getPossibleMoves(BH1);
        assertFalse(possibleMoves.isEmpty());
    }
}
