package common;

import model.Board;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

public class BoardTest {

    private final int TOTAL_SQUARES = 8 * 4 * 3; // col * row * players
    private Board board;
    private static Set<Integer> startingPiecesIndexes;

    @BeforeAll
    static void initBeforeAllBoardTest() {
        startingPiecesIndexes = new HashSet<>();
        for(int pos=0; pos<=92; pos+=4) {
            startingPiecesIndexes.add(pos); // row 1
            startingPiecesIndexes.add(pos+1); // row 2
        }
    }

    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new Board();
    }

    // Naming Convention- MethodName_StateUnderTest_ExpectedBehavior
/*    @Test
    public void isEmpty_outOfBoundIndex_Exception() {
        assertThrows(InvalidPositionException.class,
                () -> {board.isEmpty(-1);});

        assertThrows(InvalidPositionException.class,
                () -> {board.isEmpty(96);});
    }

    @Test
    public void isEmpty_nonEmptyIndex_False() throws InvalidPositionException {
        for(int pos: startingPiecesIndexes) {
            assertFalse(board.isEmpty(pos));
        }
    }*/

/*    @Test
    public void isEmpty_emptyIndex_True() throws InvalidPositionException {
        for(int pos=0; pos< TOTAL_SQUARES; pos++) {
            if(!startingPiecesIndexes.contains(pos)) {
                assertTrue(board.isEmpty(pos));
            }
        }
    }*/

    // move function currently not checking for legal move. must check it later

/*    @ParameterizedTest
    @EnumSource(Position.class)
    public void move_emptyStartPosition_noUpdateInBoard(Position startPos) {
        Map<Position, Piece> boardMapCopy = new HashMap<>(board.getBoardMap()); // copy original board map
        if(!startingPiecesIndexes.contains(startPos.getValue())) { // empty square
            board.move(startPos, Position.BA1);
            assertEquals(boardMapCopy.get(startPos), board.getBoardMap().get(startPos));
        }
    }*/

/*    @ParameterizedTest
    @EnumSource(Position.class)
    public void move_nonEmptyStartPosition_updateInBoard(Position startPos) {
        Map<Position, Piece> boardMapCopy = new HashMap<>(board.getBoardMap()); // copy original board map
        Map<Position, Piece> boardMap = board.getBoardMap();
        if(startingPiecesIndexes.contains(startPos.getValue())) {  // non-empty square
            Position endPos = Position.BA3;
            Piece movedPiece = boardMapCopy.get(startPos);
            board.move(startPos, endPos);
            assertNull(boardMap.get(startPos));
            assertEquals(movedPiece, boardMap.get(endPos));
        }
    }*/
}
