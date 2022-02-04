package game.position;

import game.Cell;
import game.Move;

public interface Position {
    Cell getTurn();

    boolean isValid(Move move);

    Cell getCell(int row, int column);

    int getHeight();

    int getWeight();
}
