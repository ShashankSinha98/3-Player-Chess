package application.controller;

import abstraction.IGameInterface;
import common.InvalidPositionException;
import common.OnClickResponse;
import main.GameMain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * GameController class - interacts with the backend logic.
 * New game instances are created here.
 **/
@RestController
public class GameController {
    private IGameInterface game;

    /**
     * Method to create new game instance
     **/
    @GetMapping("/newGame")
    public void handleNewGame(){
        System.out.println("New Game");
        this.game = new GameMain();
    }

    /**
     * Method to notify click events to the backend
     **/
    @PostMapping("/onClick")
    public OnClickResponse handleMove(@RequestBody String polygonText) throws InvalidPositionException {
        System.out.println("Polygon: " + polygonText);
        return game.onClick(polygonText);
    }

    /**
     * Method to fetch the current player information from backend
     **/
    @GetMapping("/currentPlayer")
    public String handlePlayerTurn(){
        System.out.println("Requesting current player");
        return game.getTurn().toString();
    }

    /**
     * Method to fetch the current board information from backend
     **/
    @GetMapping("/board")
    public Map<String, String> handleBoardRequest(){
        return game.getBoard();
    }


}
