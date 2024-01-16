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
    public OnClickResponse handleMove(@RequestBody String squareText) throws InvalidPositionException {
        //int squareId = calculateSquareId(squareText);

        System.out.println("Square: " + squareText);
        return game.onClick(squareText);
    }

//    @PostMapping("/allMoves")
//    public List<String> handleAllMoves(@RequestBody String squareText) {
//        System.out.println("Requesting all possible moves of: " + squareText);
//        return game.getHighlightSquarePositions();
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
