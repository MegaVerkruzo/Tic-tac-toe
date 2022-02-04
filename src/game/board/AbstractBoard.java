package game.board;

import game.Cell;
import game.GameResult;
import game.Move;
import game.BoardWithPosition;

import java.util.Arrays;
import java.util.Map;

public abstract class AbstractBoard implements BoardWithPosition {
    protected Cell[][] field;
    protected int height, weight, k;
    protected static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.Empty, ".",
            Cell.X, "X",
            Cell.O, "0"
    );
    private Cell turn;
    private int movesCount = 0;

    AbstractBoard(int height, int weight, int k) {
        this.height = height;
        this.weight = weight;
        this.k = k;
        this.field = field = new Cell[height][weight];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.Empty);
        }
        this.turn = Cell.X;
    }

    protected String numberToString(int number) {
        StringBuilder sb = new StringBuilder();
        sb.append(number);
        return sb.toString();
    }

    protected void writeSpaces(StringBuilder sb, int count) {
        sb.append(" ".repeat(count));
    }

    protected void addNumberWithSpacesAfter(StringBuilder sb, int number, int spacesCount) {
        String stringNumber = numberToString(number);
        addStringWithSpacesAfter(sb, stringNumber, spacesCount);
    }

    protected void addStringWithSpacesAfter(StringBuilder sb, String string, int spacesCount) {
        sb.append(string);
        writeSpaces(sb, spacesCount - string.length());
    }

    protected boolean isMiddleNumber(int a, int b, int x) {
        return a <= x && x <= b;
    }

    protected boolean isInsideTable(int row, int column) {
        return isMiddleNumber(0, height - 1, row) &&
                isMiddleNumber(0, weight - 1, column);
    }

    protected boolean isValid(int row, int column) {
        return isInsideTable(row, column) && getCell(row, column) == Cell.Empty;
    }

    private Cell whoseCell(int row, int column) {
        if (isValid(row, column)) {
            return Cell.Empty;
        }
        return getCell(row, column);
    }

    protected boolean checkWinOnLine(Move move, int firstCoefficient, int secondCoefficient) {
        int x = move.getRow();
        int y = move.getCol();
        Cell turn = move.getValue();
        int count = 0;
        for (int i = -(k - 1); i < k; i++) {
            if (whoseCell(x + firstCoefficient * i, y + secondCoefficient * i) == turn) {
                count++;
            } else {
                count = 0;
            }
            if (count == k) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkRowWin(Move move) {
        return checkWinOnLine(move, -1, 0);
    }

    protected boolean checkColWin(Move move) {
        return checkWinOnLine(move, 0, -1);
    }

    protected boolean checkSideDiagonalWin(Move move) {
        return checkWinOnLine(move, 1, -1);
    }

    protected abstract boolean checkWin(Move move);

    public void clearBoard() {
        movesCount = 0;
        for (int i = 0; i < height; i++) {
            Arrays.fill(field[i], Cell.Empty);
        }
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }
        field[move.getRow()][move.getCol()] = move.getValue();
        movesCount++;
        if (checkWin(move)) {
            return GameResult.WIN;
        }
        if (movesCount == height * weight) {
            return GameResult.DRAW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return GameResult.UNKNOWN;
    }

    @Override
    public Cell getTurn() {
        return this.turn;
    }

    @Override
    public boolean isValid(Move move) {
        return isValid(move.getRow(), move.getCol());
    }

    @Override
    public Cell getCell(int row, int column) {
        return isInsideTable(row, column) ? field[row][column] : Cell.Occupied;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWeight() {
        return weight;
    }
}
