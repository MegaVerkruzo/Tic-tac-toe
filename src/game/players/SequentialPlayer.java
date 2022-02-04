package game.players;

import game.Move;
import game.position.Position;

public class SequentialPlayer implements Player {
    @Override
    public Move makeMove(Position position) {
        for (int row = 0; row < position.getHeight(); row++) {
            for (int column = 0; column < position.getWeight(); column++) {
                final Move move = new Move(row, column, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }
}
