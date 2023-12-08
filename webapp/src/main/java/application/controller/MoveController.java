package application.controller;

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
    @PostMapping("/move")
    public Map<Square, Piece> handleMove(@RequestBody String squareText) {
        System.out.println("valid end square: " + squareText);
        Map<Square, Piece> board = Board.getInitialPosition();
//        Square square = new Square(Color.R, "d2");
//        Square square1 = new Square(Color.R, "d4");
//        Piece piece = board.get(square);
//        board.remove(square);
//        board.put(square1, piece);
        //board.put(square, new Piece(PieceSymbol.P, Color.R));
        //TODO pass the move to the game logic game.makeMove("Ra1", "Ra3")
        //TODO call game logic here to get the updated board
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
