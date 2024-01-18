package model;

import model.Board;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

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
/*    @Test
    public void isEmpty_outOfBoundIndex_Exception() {
        assertThrows(InvalidPositionException.class,
                () -> {board.isEmpty(-1);});

        assertThrows(InvalidPositionException.class,
                () -> {board.isEmpty(96);});
    }

    @Test
    public void isEmpty_nonEmptyIndex_False() throws InvalidPositionException {
        for(int position: startingPiecesIndexes) {
            assertFalse(board.isEmpty(position));
        }
    }*/

/*    @Test
    public void isEmpty_emptyIndex_True() throws InvalidPositionException {
        for(int position=0; position< TOTAL_SQUARES; position++) {
            if(!startingPiecesIndexes.contains(position)) {
                assertTrue(board.isEmpty(position));
            }
        }
    }*/

    // move function currently not checking for legal move. must check it later

/*    @ParameterizedTest
    @EnumSource(Position.class)
    public void move_emptyStartPosition_noUpdateInBoard(Position startPos) {
        Map<Position, Piece> boardMapCopy = new HashMap<>(board.getBoardMap()); // copy original board map
        if(!startingPiecesIndexes.contains(startPos.getValue())) { // empty polygon
            board.move(startPos, Position.BA1);
            assertEquals(boardMapCopy.get(startPos), board.getBoardMap().get(startPos));
        }
    }*/

/*    @ParameterizedTest
    @EnumSource(Position.class)
    public void move_nonEmptyStartPosition_updateInBoard(Position startPos) {
        Map<Position, Piece> boardMapCopy = new HashMap<>(board.getBoardMap()); // copy original board map
        Map<Position, Piece> boardMap = board.getBoardMap();
        if(startingPiecesIndexes.contains(startPos.getValue())) {  // non-empty polygon
            Position endPos = Position.BA3;
            Piece movedPiece = boardMapCopy.get(startPos);
            board.move(startPos, endPos);
            assertNull(boardMap.get(startPos));
            assertEquals(movedPiece, boardMap.get(endPos));
        }
    }*/
}
