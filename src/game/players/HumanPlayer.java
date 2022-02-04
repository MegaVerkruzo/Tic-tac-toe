package game.players;

import game.Move;
import game.position.Position;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println("-------------------------");
        System.out.println("Current position\n");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());
        int firstMove = -1;
        int secondMove = -1;
        Move move;
        do {
            try {
                if (firstMove == -1) {
                    firstMove = in.nextInt();
                } else if (secondMove == -1) {
                    secondMove = in.nextInt();
                } else {
                    System.err.println("Uncorrect move");
                    firstMove = secondMove = -1;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid data type entered, enter again");
                in.nextLine();
                firstMove = -1;
            }
            move = new Move(firstMove - 1, secondMove - 1, position.getTurn());
        } while (firstMove == -1 || secondMove == -1 || !position.isValid(move));

        return new Move(firstMove - 1, secondMove - 1, position.getTurn());
    }
}
