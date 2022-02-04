package game.position;

import game.BoardWithPosition;
import game.Cell;
import game.Move;

public class OnlyPosition implements Position {
    private BoardWithPosition board;

    public OnlyPosition(BoardWithPosition board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return board.toString();
    }

    @Override
    public int getHeight() {
        return board.getHeight();
    }

    @Override
    public int getWeight() {
        return board.getWeight();
    }

    @Override
    public Cell getTurn() {
        return board.getTurn();
    }

    @Override
    public boolean isValid(Move move) {
        return board.isValid(move);
    }

    @Override
    public Cell getCell(int row, int column) {
        return board.getCell(row, column);
    }
}
