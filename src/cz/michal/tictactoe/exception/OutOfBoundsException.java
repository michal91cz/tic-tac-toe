package cz.michal.tictactoe.exception;

public class OutOfBoundsException extends RuntimeException {

    public OutOfBoundsException(int number) {
        super(String.format("Pozici (%d) není možné vybrat. Je nutné vybrat číslo v rozmezí 1-9.", number));
    }
    public OutOfBoundsException(String number) {
        super(String.format("Pozici (%s) není možné vybrat. Je nutné vybrat číslo v rozmezí 1-9.", number));
    }
}
