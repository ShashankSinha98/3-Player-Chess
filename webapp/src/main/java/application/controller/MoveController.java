package application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import application.gamelogic.*;
import java.util.Map;


@RestController
public class MoveController {
    @PostMapping("/move")
    public Map<Square, Piece> handleMove(@RequestBody String squareText) {
        System.out.println("Square: " + squareText);
        Map<Square, Piece> board = Board.getInitialPosition();
        Square square = new Square(Color.R, "d2");
        Square square1 = new Square(Color.R, "d4");
        Piece piece = board.get(square);
        board.remove(square);
        board.put(square1, piece);
        //board.put(square, new Piece(PieceSymbol.P, Color.R));
        return board;
    }

}
