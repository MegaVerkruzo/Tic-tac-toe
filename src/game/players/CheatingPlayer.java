package game.players;

import game.Move;
import game.position.Position;
import game.board.MNKBoard;

public class CheatingPlayer implements Player {
    @Override
    public Move makeMove(Position position) {
        final MNKBoard board = (MNKBoard) position;
        Move first = null;
        for (int row = 0; row < position.getHeight(); row++) {
            for (int column = 0; column < position.getWeight(); column++) {
                final Move move = new Move(row, column, position.getTurn());
                if (position.isValid(move)) {
                    if (first == null) {
                        first = move;
                    } else {
                        board.makeMove(move);
                    }
                }
            }
        }
        return first;
    }
}
