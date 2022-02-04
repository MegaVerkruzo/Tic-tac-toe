package game.board;

import game.GameResult;
import game.Move;
import game.position.Position;

public interface Board {
    Position getPosition();

    GameResult makeMove(Move move);

    void clearBoard();
}
