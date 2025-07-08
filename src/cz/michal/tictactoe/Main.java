package cz.michal.tictactoe;

import cz.michal.tictactoe.exception.FieldOccupiedException;
import cz.michal.tictactoe.exception.OutOfBoundsException;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while (true) {
            System.out.println("Zvol typ hry z následujících možností.");
            System.out.println("(1) - Hotseat (lokální multiplayer)");
            System.out.println("(2) - Singleplayer");
            System.out.println("(3) - AI exhibice");
            System.out.println("(0) - Konec");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            Random rand = new Random();
            if (choice.equals("1")) {
                playGame(scanner, true, true, rand);
            } else if (choice.equals("2")) {
                playGame(scanner, true, false, rand);
            } else if (choice.equals("3")) {
                playGame(scanner, false, false, rand);
            } else if (choice.equals("0")) {
                break;
            } else {
                System.out.println("Byla zadána nerozpoznaná hodnota. Opakuj volbu.\n\n");
            }
        }
    }

    //  TODO snížit komplexitu
    /**
     * @param scanner Scanner používaný v programu
     * @param player1 Je hráč 1 člověk? Ano - TRUE, ne - FALSE
     * @param player2 Je hráč 2 člověk? Ano - TRUE, ne - FALSE
     * @param rand    Random používaný v programu
     */
    private static void playGame(Scanner scanner, boolean player1, boolean player2, Random rand) {
        Opponent opponent = new Opponent();
        Board board = new Board();
        board.printBoard();
        //  Nastavení začínajících kamenů. X vždy začíná
        StoneEnum activePlayer = StoneEnum.getStartingStone();

        boolean playerX = false;
        boolean playerO = false;
        StoneEnum winner;
        boolean draw;

        //  Náhodné přidělení kamenů hráčům/počítačům
        boolean coinFlip = board.coinFlip(rand);
        if ((player1 == player2) && player1) {
            playerX = true;
            playerO = true;
        } else if ((player1 != player2)) {
            playerX = coinFlip;
            playerO = !coinFlip;
        }

        while (true) {
            String move;
            System.out.print("Na tahu je hráč s '" + activePlayer + "'.");
            if ((playerX && StoneEnum.X.equals(activePlayer)) || (playerO && StoneEnum.O.equals(activePlayer))) {
                //  Hraje hráč
                System.out.println(" Vyber polohu pro svůj tah:");
                move = scanner.nextLine();
            } else {
                //  Hraje počítač
                move = opponent.playRandomMove(board, rand);
                System.out.println(" Počítat zvolil tah " + move);
            }
            try {
                //  Program se pokusí umístit tah. Pokud se nepodaří, hraje stejný hráč znovu.
                board.placeStone(activePlayer, move);
                //  Pokud se povedlo, nastaví se protistrana za aktivního hráče
                activePlayer = StoneEnum.getOppositeStone(activePlayer);
            } catch (OutOfBoundsException | FieldOccupiedException e) {
                //  Exception, pokud je tah ilegální. Aktivní hráč je stále původní.
                System.out.println(e.getMessage());
            }
            board.printBoard();

            //  Kontrola vítězství
            winner = board.checkVictoryCondition();
            if (winner != null) {
                System.out.println("Vítězem je " + winner + "\n\n");
                break;
            }
            //  Kontrola remízy
            draw = !board.containsEmptyField();
            if (draw) {
                System.out.println("Hra skončila remízou.\n\n");
                break;
            }
        }
    }
}