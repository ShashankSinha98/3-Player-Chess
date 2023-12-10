package application.controller;

import abstraction.IGameInterface;
import common.ImpossiblePositionException;
import main.GameMain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import application.gamelogic.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestController
public class MoveController {
    private IGameInterface game;

    public MoveController() {
        this.game = new GameMain();
    }

    private int calculateSquareId(String square){
        char color = square.charAt(0);
        int y = square.charAt(1) - 'a';
        int x = square.charAt(2) - '1';
        int offset = 0;
        if(color == 'G'){
            offset = 32;
        }else if(color == 'R'){
            offset = 64;
        }

        return offset + x + 4*y;
    }

    @PostMapping("/move")
    public Map<String, String> handleMove(@RequestBody String squareText) throws ImpossiblePositionException {
        int squareId = calculateSquareId(squareText);

        System.out.println("Square: " + squareText + " with ID " +squareId);
        Map<String, String> board = game.onClick(squareId);

        return board;
    }

    @PostMapping("/allMoves")
    public List<String> handleAllMoves(@RequestBody String squareText) {
        System.out.println("Requesting all possible moves of: " + squareText);
        String squareId = squareText.substring(1);
        List<String> allMoves = new ArrayList<>(Arrays.asList('R' + squareId,'G' + squareId, 'B' + squareId));
        allMoves.remove(squareText);
        //TODO call game logic here to get all possible Moves
        System.out.println("Response: " + allMoves);
        return allMoves;
    }

//    @GetMapping("/playerTurn")
//    public String handlePlayerTurn(){
//        System.out.println("Requesting player turn");
//        //TODO call game logic here to get the updated board
//        return "";
//    }


}
