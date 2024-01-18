package application.controller;

import abstraction.IGameInterface;
import common.InvalidPositionException;
import common.OnClickResponse;
import main.GameMain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class GameController {
    private IGameInterface game;

    public GameController() {
        //this.game = new GameMain();
    }

    @GetMapping("/newGame")
    public void handleNewGame(){
        System.out.println("New Game");
        this.game = new GameMain();
    }

    @PostMapping("/onClick")
    public OnClickResponse handleMove(@RequestBody String polygonText) throws InvalidPositionException {
        //int polygonId = calculatePolygonId(polygonText);

        System.out.println("Polygon: " + polygonText);
        return game.onClick(polygonText);
    }

//    @PostMapping("/allMoves")
//    public List<String> handleAllMoves(@RequestBody String polygonText) {
//        System.out.println("Requesting all possible moves of: " + polygonText);
//        return game.getHighlightPolygonPositions();
//    }

    @GetMapping("/currentPlayer")
    public String handlePlayerTurn(){
        System.out.println("Requesting current player");
        return game.getTurn().toString();
    }

    @GetMapping("/board")
    public Map<String, String> handleBoardRequest(){
        return game.getBoard();
    }


}
