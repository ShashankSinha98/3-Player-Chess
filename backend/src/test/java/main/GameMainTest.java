package main;

import common.Colour;
import common.OnClickResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameMainTest {

    private GameMain gameMain;

    @BeforeEach
    void initBeforeEachBoardTest() {
        gameMain = new GameMain();
    }

    @Test
    public void onClick_selectEmptyPolygon_noHighlight() {
        OnClickResponse response = gameMain.onClick("Bc3");
        assertEquals(0, response.getHighlightedPolygons().size());
    }

    @Test
    public void onClick_selectBluePawnPolygon_highlightListNonEmpty() {
        OnClickResponse response = gameMain.onClick("Ba2");
        assertTrue(response.getHighlightedPolygons().size()>0);
    }

    @Test
    public void onClick_selectNonTurnRedPawnPolygon_noHighlight() {
        OnClickResponse response = gameMain.onClick("Ra2");
        assertEquals(0, response.getHighlightedPolygons().size());
    }

    @Test
    public void onClick_moveBluePawn_noHighlight() {
        gameMain.onClick("Bb2");
        OnClickResponse response = gameMain.onClick("Bb4");
        assertEquals(0, response.getHighlightedPolygons().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Kb2", "", "123", "Ri33", "RBB"})
    public void onClick_invalidPolygonLabel_noHighlightNoBoardChange(String polygonLabel) {
        Map<String, String> oldBoard = gameMain.getBoard();
        OnClickResponse response = gameMain.onClick(polygonLabel);
        assertEquals(0, response.getHighlightedPolygons().size());
        assertEquals(oldBoard, gameMain.getBoard());
    }

    @Test
    public void onClick_invalidMove_noHighlightNoBoardChange() {
        Map<String, String> oldBoard = gameMain.getBoard();
        gameMain.onClick("Ba2");
        OnClickResponse response = gameMain.onClick("Ba4");
        assertEquals(0, response.getHighlightedPolygons().size());
        assertEquals(oldBoard, gameMain.getBoard());
    }

    @Test
    public void getTurn_getTurnOnGameStart_blueTurn() {
        assertEquals(Colour.BLUE, gameMain.getTurn());
    }

    @Test
    public void getTurn_getTurnAfterOneValidMove_greenTurn() {
        gameMain.onClick("Bb2");
        gameMain.onClick("Bb4");
        assertEquals(Colour.GREEN, gameMain.getTurn());
    }

    @Test
    public void getTurn_getTurnAfterTwoValidMove_greenTurn() {
        gameMain.onClick("Bb2");
        gameMain.onClick("Bb4");
        gameMain.onClick("Gb2");
        gameMain.onClick("Gb4");
        assertEquals(Colour.RED, gameMain.getTurn());
    }

    @Test
    public void getTurn_getTurnAfterThreeValidMove_greenTurn() {
        gameMain.onClick("Bb2");
        gameMain.onClick("Bb4");
        gameMain.onClick("Gb2");
        gameMain.onClick("Gb4");
        gameMain.onClick("Rb2");
        gameMain.onClick("Rb4");
        assertEquals(Colour.BLUE, gameMain.getTurn());
    }

}
