package game;

import game.board.HexBoard;
import game.board.MNKBoard;
import game.players.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static void printInstructionsForBoard() {
        printDelimiter();
        System.out.println("1 - MNKBoard;\n2 - HEXBoard;");
        System.out.println("Choose number of field:\n(Write without brackets, for example: \"1\")");
    }

    private static void printInstructionsForInitialBoard() {
        printDelimiter();
        System.out.println("m, n - sizes of field;\nk - minimum number tokens for win;");
        System.out.println("Enter m, n, k:\n(Write without brackets, for example: \"3 3 3\")");
    }

    private static void printInstructionsForCountPlayers() {
        printDelimiter();
        System.out.println("Enter count of players:\n(Write without brackets, for example: \"4\")");
    }

    private static void printInstructionForPlayersType(boolean wasMistake, int countOfRemainingPlayers, boolean withoutHumanPlayer) {
        printDelimiter();
        if (wasMistake) {
            System.out.print("Number of players without information: ");
            System.out.println(countOfRemainingPlayers);
        }
        System.out.println("1 - RandomPlayer;");
        System.out.println("2 - CheatingPlayer;");
        System.out.println("3 - SequentialPlayer;");
        if (!withoutHumanPlayer) {
            System.out.println("4 - HumanPlayer (You can add this player at once);");
        }
        System.out.println("Choose number of player:\n(Write without brackets, for example: \"1\")");
    }

    private static void printInstructionForLog() {
        printDelimiter();
        System.out.println("1 - watch the game");
        System.out.println("2 - skip every move except your own");
        System.out.println("Choose number:\n(Write without brackets, for example: \"2\")");
    }

    private static void printIncorrectInput(Scanner sc) {
        System.err.println("Incorrect input, try again");
        sc.nextLine();
    }

    private static void printDelimiter() {
        System.out.println("-------------------------");
    }

    private static boolean showLog(int determiner) {
        return determiner == 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        printInstructionsForBoard();
        int typeOfBoard = 0;
        do {
            try {
                typeOfBoard = sc.nextInt();
            } catch (InputMismatchException e) {
                typeOfBoard = -1;
            } finally {
                if (!(1 <= typeOfBoard && typeOfBoard <= 2)) {
                    printIncorrectInput(sc);
                    printInstructionsForBoard();
                }
            }
        } while (typeOfBoard != 1 && typeOfBoard != 2);
        printInstructionsForInitialBoard();
        int m, n, k;
        m = n = k = -1;
        do {
            try {
                m = sc.nextInt();
                n = sc.nextInt();
                k = sc.nextInt();
            } catch (InputMismatchException e) {
                m = n = k = -1;
            } finally {
                if (m <= 0 || n <= 0 || k <= 0) {
                    printIncorrectInput(sc);
                    printInstructionsForInitialBoard();
                }
            }
        } while (n <= -1 || m <= -1 || k <= -1);
        int countOfPlayers = 0;
        printInstructionsForCountPlayers();
        do {
            try {
                countOfPlayers = sc.nextInt();
            } catch (InputMismatchException e) {
                countOfPlayers = 0;
            } finally {
                if (countOfPlayers <= 1) {
                    printIncorrectInput(sc);
                    printInstructionsForCountPlayers();
                }
            }
        } while (countOfPlayers <= 1);
        PlayerList listOfPlayers = new PlayerList();
        boolean wasHumanPlayer = false;
        printInstructionForPlayersType(false, 0, wasHumanPlayer);
        for (int i = 0; i < countOfPlayers; i++) {
            int typeOfPlayer = 0;
            do {
                try {
                    typeOfPlayer = sc.nextInt();
                } catch (InputMismatchException e) {
                    typeOfPlayer = 0;
                } finally {
                    if (!(1 <= typeOfPlayer && typeOfPlayer <= 4) || wasHumanPlayer && typeOfPlayer == 4) {
                        printIncorrectInput(sc);
                        printInstructionForPlayersType(true, countOfPlayers - i, wasHumanPlayer);
                        typeOfPlayer = 0;
                    }
                }
            } while (!(1 <= typeOfPlayer && typeOfPlayer <= 4) || wasHumanPlayer && typeOfPlayer == 4);
            switch (typeOfPlayer) {
                case 1:
                    listOfPlayers.add(new RandomPlayer());
                    break;
                case 2:
                    listOfPlayers.add(new CheatingPlayer());
                    break;
                case 3:
                    listOfPlayers.add(new SequentialPlayer());
                    break;
                case 4:
                    wasHumanPlayer = true;
                    listOfPlayers.add(new HumanPlayer(sc));
                    break;
            }
        }
        printInstructionForLog();
        int showLog = 0;
        do {
            try {
                showLog = sc.nextInt();
            } catch (InputMismatchException e) {
                showLog = 0;
            } finally {
                if (showLog != 1 && showLog != 2) {
                    printIncorrectInput(sc);
                    printInstructionForLog();
                }
            }
        } while (showLog != 1 && showLog != 2);
        final Tournament game = new Tournament(
                typeOfBoard == 1 ? new MNKBoard(m, n, k) : new HexBoard(m, n, k),
                listOfPlayers.getArray());
        game.play(showLog(showLog));
        game.writeScores();
    }
}
