package game.board;

import game.Cell;
import game.Move;
import game.position.OnlyPosition;
import game.position.Position;

public class MNKBoard extends AbstractBoard implements Board, Position {
    public MNKBoard(int height, int weight, int k) {
        super(height, weight, k);
    }

    private boolean checkMainDiagonalWin(Move move) {
        return checkWinOnLine(move, 1, 1);
    }

    @Override
    protected boolean checkWin(Move move) {
        return checkColWin(move) || checkRowWin(move) || checkMainDiagonalWin(move) || checkSideDiagonalWin(move);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        int firstIndentLen = numberToString(height).length() + 1;
        int spacesBetweenColumns = numberToString(weight).length() + 1;
        writeSpaces(sb, firstIndentLen);
        for (int i = 1; i <= weight; i++) {
            addNumberWithSpacesAfter(sb, i, spacesBetweenColumns);
        }
        sb.append(System.lineSeparator());
        for (int i = 1; i <= height; i++) {
            addNumberWithSpacesAfter(sb, i, firstIndentLen);
            for (Cell cell : field[i - 1]) {
                addStringWithSpacesAfter(sb, super.CELL_TO_STRING.get(cell), spacesBetweenColumns);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public Position getPosition() {
        return new OnlyPosition(this);
    }
}
