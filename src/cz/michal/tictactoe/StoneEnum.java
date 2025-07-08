package cz.michal.tictactoe;

public enum StoneEnum {
    X,
    O,
    N;

    public static StoneEnum getStartingStone() {
        return X;
    }
    public static StoneEnum getOppositeStone(StoneEnum stone) {
        if (stone == X) return O;
        if (stone == O) return X;
        return null;
    }
}
