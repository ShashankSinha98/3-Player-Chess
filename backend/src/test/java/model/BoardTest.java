package model;

import common.Colour;
import common.InvalidMoveException;
import common.InvalidPositionException;
import common.Position;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static common.Position.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private final int TOTAL_SQUARES = 8 * 4 * 3; // col * row * players
    private Board board;
    private static Set<Integer> startingPiecesIndexes;

    @BeforeAll
    static void initBeforeAllBoardTest() {
        startingPiecesIndexes = new HashSet<>();
        for(int position=0; position<=92; position+=4) {
            startingPiecesIndexes.add(position); // row 1
            startingPiecesIndexes.add(position+1); // row 2
        }
    }

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    // Naming Convention- MethodName_StateUnderTest_ExpectedBehavior
    @Test
    public void move_pieceMoveToEmptyPolygon_startPositionEmptyAndEndPositionOccupied() throws InvalidPositionException, InvalidMoveException {
        BasePiece pawn = board.boardMap.get(BE2);
        board.move(BE2, BE4);
        assertNull(board.boardMap.get(BE2));
        assertNotNull(board.boardMap.get(BE4));
        assertEquals(pawn, board.boardMap.get(BE4));
    }

    @Test
    public void move_wallMoveToEmptyPolygon_startPositionEmptyAndEndPositionOccupiedAndWallMappingUpdate() throws InvalidPositionException, InvalidMoveException {
        BasePiece wall = board.boardMap.get(BH2);
        assertInstanceOf(Wall.class, wall);
        assertEquals(Colour.BLUE, wall.getColour());

        board.move(BH2, BH4);
        assertNull(board.boardMap.get(BH2));
        assertNotNull(board.boardMap.get(BH4));
        assertEquals(wall, board.boardMap.get(BH4));
        assertEquals(BH4, board.wallPieceMapping.get(wall));
    }

    @Test
    public void move_pawnToOppositeEndRow_pawnUpgradeToQueen() throws InvalidPositionException, InvalidMoveException {
        BasePiece bluePawn = new Pawn(Colour.BLUE);
        board.boardMap.put(RA2, bluePawn);
        board.boardMap.remove(RA1); // empty RA1 for blue pawn to move
        board.move(RA2, RA1);

        BasePiece promotedPiece = board.boardMap.get(RA1);
        assertInstanceOf(Queen.class, promotedPiece);
    }

    @Test
    public void move_jesterTakesWall_jesterWallPositionSwitchAndWallPieceMappingUpdates() throws InvalidPositionException, InvalidMoveException {
        BasePiece blueJester = new Jester(Colour.BLUE);
        board.boardMap.put(BE4, blueJester);

        BasePiece redWall = board.boardMap.get(RH2);

        board.boardMap.put(RC3, redWall);
        board.move(BE4, RC3);
        assertEquals(redWall, board.boardMap.get(BE4));
        assertEquals(blueJester, board.boardMap.get(RC3));
        assertEquals(BE4, board.wallPieceMapping.get(redWall));
    }

    @Test
    public void move_rightCastlingLegalMove_castlingHappen() throws InvalidPositionException, InvalidMoveException {
        board.boardMap.remove(BF1);
        board.boardMap.remove(BG1);

        BasePiece king = board.boardMap.get(BE1);
        BasePiece rightRook = board.boardMap.get(BH1);

        board.move(BE1, BG1); // left castling
        assertEquals(king, board.boardMap.get(BG1));
        assertEquals(rightRook, board.boardMap.get(BF1));
    }

    @Test
    public void move_leftCastlingLegalMove_castlingHappen() throws InvalidPositionException, InvalidMoveException {
        board.boardMap.remove(BD1);
        board.boardMap.remove(BC1);
        board.boardMap.remove(BB1);

        BasePiece king = board.boardMap.get(BE1);
        BasePiece leftRook = board.boardMap.get(BA1);

        board.move(BE1, BC1); // left castling
        assertEquals(king, board.boardMap.get(BC1));
        assertEquals(leftRook, board.boardMap.get(BD1));
    }

    @Test
    public void move_bluePieceTakesRedKing_gameOverAndBlueWinner() throws InvalidPositionException, InvalidMoveException {
        BasePiece blueRook = new Rook(Colour.BLUE);
        board.boardMap.put(RE2, blueRook);

        board.move(RE2, RE1);

        assertTrue(board.isGameOver());
        assertEquals("B", board.getWinner());
    }

    @Test
    public void getPossibleMoves_emptyPosition_emptyPositionsList() {
        Set<Position> possibleMoves = board.getPossibleMoves(BE4);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void getPossibleMoves_rookBehindJesterInitialPosition_emptyPositionsList() {
        Set<Position> possibleMoves = board.getPossibleMoves(BA1);
        assertTrue(possibleMoves.isEmpty());
    }

    @Test
    public void getPossibleMoves_rookBehindWallInitialPosition_nonEmptyPositionsList() {
        Set<Position> possibleMoves = board.getPossibleMoves(BH1);
        assertFalse(possibleMoves.isEmpty());
    }
}
