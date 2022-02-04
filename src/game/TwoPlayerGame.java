package game;

import game.board.Board;
import game.players.Player;

public class TwoPlayerGame {
    private final Board board;
    private final Player player1;
    private final Player player2;

    public TwoPlayerGame(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(boolean log, int firstPlayerNumber, int secondPlayerNumber) {
        while (true) {
            final int result1 = makeMove(player1, firstPlayerNumber, secondPlayerNumber, log);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = makeMove(player2, secondPlayerNumber, firstPlayerNumber, log);
            if (result2 != -1) {
                return result2;
            }
        }
    }

    private int makeMove(Player player, int currentPlayer, int anotherPlayer, boolean log) {
        final Move move = player.makeMove(board.getPosition());
        final GameResult result = board.makeMove(move);
        if (log) {
            System.out.println("-------------------------");
            System.out.println("Player: " + currentPlayer);
            System.out.println(move);
            System.out.println(board);
            System.out.println("Result: " + result);
        }
        switch (result) {
            case WIN:
                return currentPlayer;
            case LOOSE:
                return anotherPlayer;
            case DRAW:
                return 0;
            case UNKNOWN:
                return -1;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}
