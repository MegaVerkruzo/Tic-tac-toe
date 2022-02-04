package game.board;

import game.Cell;
import game.Move;
import game.position.OnlyPosition;
import game.position.Position;

public class HexBoard extends AbstractBoard implements Board, Position {
    public HexBoard(int height, int weight, int k) {
        super(height, weight, k);
    }

    private boolean checkRightNumber(int lenOfTable, int i, int j) {
        return i + 1 < lenOfTable && j + 1 < lenOfTable + 1 && checkField(i + 1, j + 1);
    }

    private boolean checkLeftNumber(int lenOfTable, int i, int j) {
        return i + 1 < lenOfTable && j - 1 >= 0 && checkField(i + 1, j - 1);
    }

    private boolean checkField(int i, int j) {
        return ((height + 1) % 2 == (i + j) % 2
                && height + 1 <= i + j && i + j <= (height + 1) + 2 * (weight - 1)
                && 1 - height <= (j - i) && (j - i) <= (height - 1));
    }

    private boolean isUpperLeftBorder(int rowInHex, int columnInHex) {
        return !(columnInHex + rowInHex > height + 1);
    }

    private boolean isUpperElement(int rowInHex, int columnInHex) {
        return rowInHex == 1 && columnInHex == height;
    }

    private Cell getCellInHex(int rowInHex, int columnInHex) {
        int columnTable = 0;
        int rowTable = 0;
        while (!isUpperElement(rowInHex, columnInHex)) {
            if (!isUpperLeftBorder(rowInHex, columnInHex)) {
                columnInHex--;
                columnTable++;
            } else {
                columnInHex++;
                rowTable++;
            }
            rowInHex--;
        }
        return super.getCell(rowTable, columnTable);
    }

    @Override
    protected boolean checkWin(Move move) {
        return checkColWin(move) || checkRowWin(move) || checkSideDiagonalWin(move);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        int lenOfTable = weight + height;
        int nowWeight = 1;
        int nowHeight = 1;
        int spacesBetweenColumns = numberToString(Math.max(height, weight)).length() + 1;
        for (int i = 0; i < lenOfTable; i++) {
            for (int j = 0; j < lenOfTable + 1; j++) {
                if (checkField(i, j)) {
                    addStringWithSpacesAfter(sb, super.CELL_TO_STRING.get(getCellInHex(i, j)), spacesBetweenColumns);
                } else if (checkRightNumber(lenOfTable, i, j)) {
                    addNumberWithSpacesAfter(sb, nowHeight++, spacesBetweenColumns);
                } else if (checkLeftNumber(lenOfTable, i, j)) {
                    addNumberWithSpacesAfter(sb, nowWeight++, spacesBetweenColumns);
                } else {
                    writeSpaces(sb, spacesBetweenColumns);
                }
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
