package cz.michal.tictactoe;

import java.util.List;
import java.util.Random;

public class Opponent {
    public String playRandomMove(Board board, Random rand) {
        List<Integer> possibleMoveList = board.getEmptyFieldList();

        //  Umělá inteligence :)
        if (possibleMoveList.contains(5)) {
            return "5";
        }

        return possibleMoveList.get(rand.nextInt(possibleMoveList.size())).toString();
    }
}
