package application.controller;

import abstraction.IGameInterface;
import common.Colour;
import common.GameState;
import main.GameBuilder;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param bluePlayerName name of the blue player
     * @param greenPlayerName name of the green player
     * @param redPlayerName name of the red player
     */
    @PostMapping("/newGame")
    public void handleNewGame(@RequestParam("bluePlayerName") String bluePlayerName,
                              @RequestParam("greenPlayerName") String greenPlayerName,
                              @RequestParam("redPlayerName") String redPlayerName){

        System.out.println("New Game");
        System.out.println(bluePlayerName);
        this.game = new GameBuilder()
                .setBluePlayerName(bluePlayerName)
                .setGreenPlayerName(greenPlayerName)
                .setRedPlayerName(redPlayerName)
                .build();
    }

    /**
     * Method to notify click events to the backend
     **/
    @PostMapping("/onClick")
    public GameState handleMove(@RequestBody String polygonText) {
        System.out.println("Polygon: " + polygonText);
        return game.onClick(polygonText);
    }

    /**
     * Method to fetch the current player information from backend
     **/
    @GetMapping("/currentPlayer")
    public Map.Entry<Colour, String> handlePlayerTurn(){
        System.out.println("Requesting current player");

        return game.getTurn();
    }

    /**
     * Method to fetch the current board information from backend
     **/
    @GetMapping("/board")
    public Map<String, String> handleBoardRequest(){
        return game.getBoard();
    }


}
