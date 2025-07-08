package cz.michal.tictactoe.exception;

public class FieldOccupiedException extends RuntimeException {

    public FieldOccupiedException(int number) {
        super(String.format("Na pozici (%d) již kámen umístěn je.", number));
    }
}
