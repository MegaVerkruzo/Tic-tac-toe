package game;

import game.board.Board;
import game.players.Player;

public class Tournament {
    private Board board;
    private Player[] players;
    private int playersCount;
    private int[] scores;

    Tournament(Board board, Player... players) {
        this.board = board;
        this.players = players;
        playersCount = players.length;
        scores = new int[playersCount];
    }

    public void play(boolean log) {
        for (int i = 0; i < playersCount; i++) {
            for (int j = 0; j < playersCount; j++) {
                if (i == j) {
                    continue;
                }
                if (log) {
                    System.out.print("\nGame ");
                    System.out.print(i + 1);
                    System.out.print(" player with ");
                    System.out.print(j + 1);
                    System.out.println(" player: ");
                }
                final int result = new TwoPlayerGame(
                        board,
                        players[i],
                        players[j]
                ).play(log, i + 1, j + 1);
                if (result == i + 1) {
                    scores[i] += 3;
                } else if (result == j + 1) {
                    scores[j] += 3;
                } else if (result == 0) {
                    scores[i] += 1;
                    scores[j] += 1;
                } else {
                    throw new AssertionError("Unknown result " + result);
                }
                board.clearBoard();
            }
        }
    }

    public void writeScores() {
        System.out.println("-------------------------");
        for (int i = 0; i < playersCount; i++) {
            System.out.print("Player ");
            System.out.print(i + 1);
            System.out.print(" have ");
            System.out.print(scores[i]);
            System.out.println(" scores");
        }
    }
}
