package cz.michal.tictactoe;

import cz.michal.tictactoe.exception.FieldOccupiedException;
import cz.michal.tictactoe.exception.OutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private StoneEnum[][] board;

    public StoneEnum[][] getBoard() {
        return board;
    }

    private void setBoard(StoneEnum[][] board) {
        this.board = board;
    }

    /**
     * Inicializace hrací desky
     */
    public Board() {
        board = new StoneEnum[3][3];
        int row = 0;
        int col = 0;
        for (StoneEnum[] stoneEnums : board) {
            for (StoneEnum stoneEnum : stoneEnums) {
                board[row][col] = StoneEnum.N;
                col++;
            }
            col = 0;
            row++;
        }
    }

    /**
     * Umístění kamene na desku. Probíhá validace tahu.
     *
     * @param stone    Barva kamene
     * @param position Pozice (1-9)
     * @throws OutOfBoundsException   Pozice je nevalidní - mimo rozmezí 1-9 nebo byl použit neplatný znak.
     * @throws FieldOccupiedException Pozice je obsazená.
     */
    public void placeStone(StoneEnum stone, String position) throws OutOfBoundsException, FieldOccupiedException {
        int positionInt;
        try {
            positionInt = Integer.parseInt(position);
        } catch (NumberFormatException e) {
            throw new OutOfBoundsException(position);
        }
        try {
            //  Převod int na koordináty
            int[] coordinates = getCoordinates(positionInt);
            if (!StoneEnum.N.equals(getBoard()[coordinates[0]][coordinates[1]])) {
                throw new FieldOccupiedException(positionInt);
            }
            placeStone(stone, coordinates[0], coordinates[1]);
        } catch (OutOfBoundsException e) {
            throw new OutOfBoundsException(positionInt);
        }
    }

    /**
     * Převod čísla na koordinát
     *
     * @param position Číslo pro převod
     * @return Koordinát (řada, sloupec)
     */
    private int[] getCoordinates(int position) {
        return switch (position) {
            case 1 -> new int[]{0, 0};
            case 2 -> new int[]{0, 1};
            case 3 -> new int[]{0, 2};
            case 4 -> new int[]{1, 0};
            case 5 -> new int[]{1, 1};
            case 6 -> new int[]{1, 2};
            case 7 -> new int[]{2, 0};
            case 8 -> new int[]{2, 1};
            case 9 -> new int[]{2, 2};
            default -> throw new OutOfBoundsException(position);
        };
    }

    /**
     * Převod Koordinátu na číslo
     *
     * @param row Řada
     * @param col Sloupec
     * @return Číslo pole vypočítané z koordinátu
     */
    private int getPositionNumber(int row, int col) {
        return (row * 3) + col + 1;
    }

    /**
     * Umístění kamene
     *
     * @param stoneEnum Barva kamene
     * @param row       Řada
     * @param col       Sloupec
     */
    private void placeStone(StoneEnum stoneEnum, int row, int col) {
        board[row][col] = stoneEnum;
    }

    /**
     * Kontola, zda hrací deska obsahuje volné pole
     *
     * @return Obsahuje volné pole - TRUE, jinak FALSE
     */
    public boolean containsEmptyField() {
        for (StoneEnum[] stoneEnums : board) {
            for (StoneEnum stoneEnum : stoneEnums) {
                if (stoneEnum == StoneEnum.N) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Hod mincí - TRUE/FALSE
     *
     * @param rand Random používaný v programu
     * @return Náhodné TRUE/FALSE
     */
    public boolean coinFlip(Random rand) {
        int randomInt = rand.nextInt(2);
        return randomInt != 0;
    }

    /**
     * Vrací seznam prázdných polí
     *
     * @return List s čísli prázdných polí
     */
    public List<Integer> getEmptyFieldList() {
        List<Integer> posibleMoveList = new ArrayList<>();
        int row = 0;
        int col = 0;
        for (StoneEnum[] stoneEnums : board) {
            for (StoneEnum stoneEnum : stoneEnums) {
                if (stoneEnum.equals(StoneEnum.N)) {
                    posibleMoveList.add(getPositionNumber(row, col));
                }
                col++;
            }
            col = 0;
            row++;
        }
        return posibleMoveList;
    }

    /**
     * Kontrola, zda někdo vyhrál
     *
     * @return Pokud jsou splněny podmínky vétězství,vrací StoneEnum s příslušnou hodnotou. V opačném případě null.
     */
    public StoneEnum checkVictoryCondition() {
        StoneEnum columnsWin = checkColumns(board);
        if (columnsWin != null) {
            return columnsWin;
        }
        StoneEnum rowWin = checkRows(board);
        if (rowWin != null) {
            return rowWin;
        }
        StoneEnum diagonalWin = checkDiagonals(board);
        if (diagonalWin != null) {
            return diagonalWin;
        }
        return null;
    }

    private StoneEnum checkDiagonals(StoneEnum[][] board) {
        int counterO = 0;
        int counterX = 0;
        for (int diagonal1 = 0; diagonal1 < 3; diagonal1++) {
            StoneEnum stoneEnum = board[diagonal1][diagonal1];
            if (stoneEnum == StoneEnum.N) {
                continue;
            }
            if (stoneEnum == StoneEnum.O) {
                counterO++;
            }
            if (stoneEnum == StoneEnum.X) {
                counterX++;
            }
            if (counterO == 3) {
                return StoneEnum.O;
            }
            if (counterX == 3) {
                return StoneEnum.X;
            }
        }
        counterO = 0;
        counterX = 0;

        for (int diagonal2 = 0; diagonal2 < 3; diagonal2++) {
            StoneEnum stoneEnum = board[2 - diagonal2][diagonal2];
            if (stoneEnum == StoneEnum.N) {
                continue;
            }
            if (stoneEnum == StoneEnum.O) {
                counterO++;
            }
            if (stoneEnum == StoneEnum.X) {
                counterX++;
            }
            if (counterO == 3) {
                return StoneEnum.O;
            }
            if (counterX == 3) {
                return StoneEnum.X;
            }
        }
        return null;
    }

    private StoneEnum checkRows(StoneEnum[][] board) {
        int counterO = 0;
        int counterX = 0;
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                StoneEnum stoneEnum = board[row][col];
                if (stoneEnum == StoneEnum.N) {
                    continue;
                }
                if (stoneEnum == StoneEnum.O) {
                    counterO++;
                }
                if (stoneEnum == StoneEnum.X) {
                    counterX++;
                }
            }
            if (counterO == 3) {
                return StoneEnum.O;
            }
            if (counterX == 3) {
                return StoneEnum.X;
            }
            counterO = 0;
            counterX = 0;
        }
        return null;
    }

    private StoneEnum checkColumns(StoneEnum[][] board) {
        int counterO = 0;
        int counterX = 0;
        for (StoneEnum[] stoneEnums : board) {
            for (StoneEnum stoneEnum : stoneEnums) {
                if (stoneEnum == StoneEnum.N) {
                    continue;
                }
                if (stoneEnum == StoneEnum.O) {
                    counterO++;
                }
                if (stoneEnum == StoneEnum.X) {
                    counterX++;
                }
            }
            if (counterO == 3) {
                return StoneEnum.O;
            }
            if (counterX == 3) {
                return StoneEnum.X;
            }
            counterO = 0;
            counterX = 0;
        }
        return null;
    }

    /**
     * Vymalování hrací desky do konzole.
     */
    public void printBoard() {
        for (StoneEnum[] stoneEnums : board) {
            for (StoneEnum stoneEnum : stoneEnums) {
                if (stoneEnum == StoneEnum.N) {
                    System.out.print("-");
                } else if (stoneEnum == StoneEnum.O) {
                    System.out.print("O");
                } else if (stoneEnum == StoneEnum.X) {
                    System.out.print("X");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
