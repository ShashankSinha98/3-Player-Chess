package main;

import common.Colour;
import common.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the GameMain class.
 */
 class GameMainTest {

    private GameMain gameMain;

   /**
    * Initializes a new GameMain instance before each test.
    */
    @BeforeEach
    void initBeforeEachBoardTest() {
        gameMain = new GameBuilder()
                .setBluePlayerName("A")
                .setGreenPlayerName("B")
                .setRedPlayerName("C")
                .build();
    }

   /**
    * Tests the onClick method when selecting an empty polygon, expecting no highlighted Polygons.
    */
    @Test
     void onClick_selectEmptyPolygon_noHighlight() {
        GameState response = gameMain.onClick("Bc3");
        assertEquals(0, response.getHighlightedPolygons().size());
    }

   /**
    * Tests the onClick method when selecting a blue pawn polygon, expecting highlight.
    */
    @Test
     void onClick_selectBluePawnPolygon_highlightListNonEmpty() {
        GameState response = gameMain.onClick("Ba2");
        assertFalse(response.getHighlightedPolygons().isEmpty());
    }

   /**
    * Tests the onClick method when selecting a non-turn red pawn polygon, expecting no highlight.
    */
    @Test
     void onClick_selectNonTurnRedPawnPolygon_noHighlight() {
        GameState response = gameMain.onClick("Ra2");
        assertEquals(0, response.getHighlightedPolygons().size());
    }

   /**
    * Tests the onClick method when moving a blue pawn, expecting no highlight.
    */
    @Test
     void onClick_moveBluePawn_noHighlight() {
        gameMain.onClick("Bb2");
        GameState response = gameMain.onClick("Bb4");
        assertEquals(0, response.getHighlightedPolygons().size());
    }

   /**
    * Parameterized test for onClick method with invalid polygon labels,
    * expecting no highlight and no board change.
    *
    * @param polygonLabel Invalid polygon label to test
    */
    @ParameterizedTest
    @ValueSource(strings = {"Kb2", "", "123", "Ri33", "RBB"})
     void onClick_invalidPolygonLabel_noHighlightNoBoardChange(String polygonLabel) {
        Map<String, String> oldBoard = gameMain.getBoard();
        GameState response = gameMain.onClick(polygonLabel);
        assertEquals(0, response.getHighlightedPolygons().size());
        assertEquals(oldBoard, gameMain.getBoard());
    }

   /**
    * Tests the onClick method with an invalid move, expecting no highlight and no board change.
    */
    @Test
     void onClick_invalidMove_noHighlightNoBoardChange() {
        Map<String, String> oldBoard = gameMain.getBoard();
        gameMain.onClick("Ba2");
        GameState response = gameMain.onClick("Ba4");
        assertEquals(0, response.getHighlightedPolygons().size());
        assertEquals(oldBoard, gameMain.getBoard());
    }

   /**
    * Tests the getTurn method on game start, expecting blue turn.
    */
    @Test
     void getTurn_getTurnOnGameStart_blueTurn() {
        assertEquals(Colour.BLUE, gameMain.getTurn().getKey());
    }

   /**
    * Tests the getTurn method after one valid move, expecting green turn.
    */
    @Test
     void getTurn_getTurnAfterOneValidMove_greenTurn() {
        gameMain.onClick("Bb2");
        gameMain.onClick("Bb4");
        assertEquals(Colour.GREEN, gameMain.getTurn().getKey());
    }

   /**
    * Tests the getTurn method after two valid moves, expecting red turn.
    */
    @Test
     void getTurn_getTurnAfterTwoValidMove_redTurn() {
        gameMain.onClick("Bb2");
        gameMain.onClick("Bb4");
        gameMain.onClick("Gb2");
        gameMain.onClick("Gb4");
        assertEquals(Colour.RED, gameMain.getTurn().getKey());
    }

   /**
    * Tests the getTurn method after three valid moves, expecting blue turn.
    */
    @Test
     void getTurn_getTurnAfterThreeValidMove_blueTurn() {
        gameMain.onClick("Bb2");
        gameMain.onClick("Bb4");
        gameMain.onClick("Gb2");
        gameMain.onClick("Gb4");
        gameMain.onClick("Rb2");
        gameMain.onClick("Rb4");
        assertEquals(Colour.BLUE, gameMain.getTurn().getKey());
    }

}
