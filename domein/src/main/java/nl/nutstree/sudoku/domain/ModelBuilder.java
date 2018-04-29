package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;

import java.util.*;

public class ModelBuilder {

    public static Model create(String board) {
        SudokuSize size = createSudokuSize(board);

        Map<Integer, Cell> boardCells = createCells(board, size);
        List<Row> rows = createRows(size, boardCells);
        List<Column> columns = createColumns(size, boardCells);
        List<Square> squares = createSquares(size, boardCells);

        return new Model(size, rows, columns, squares);
    }

    private static List<Square> createSquares(SudokuSize size, Map<Integer, Cell> boardCells) {
        int rowSize = size.getValue();
        int squareRowSize = (int)Math.sqrt(rowSize);


        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < squareRowSize; i++) {
            for (int j = 0; j < squareRowSize; j++) {
                List<Cell> cells = new ArrayList<>();
                for (int k = 0; k < squareRowSize; k++) {
                    for (int l = 0; l < squareRowSize; l++) {
                        int cellPosition = (i * squareRowSize * rowSize) + (j * squareRowSize) + (k * rowSize) + l;
                        cells.add(boardCells.get(cellPosition));
                    }
                }
                squares.add(Square.create(cells));
            }
        }
        return squares;
    }

    private static List<Column> createColumns(SudokuSize size, Map<Integer, Cell> boardCells) {
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < size.getValue(); i++) {
            List<Cell> cells = new ArrayList<>();
            for (int j = 0; j < size.getValue(); j++) {
                cells.add(boardCells.get(i + (j * size.getValue())));
            }
            columns.add(Column.create(cells));
        }
        return columns;
    }

    private static List<Row> createRows(SudokuSize size, Map<Integer, Cell> boardCells) {
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < size.getValue(); i++) {
            List<Cell> cells = new ArrayList<>();
            for (int j = 0; j < size.getValue(); j++) {
                cells.add(boardCells.get((i*size.getValue())+j));
            }
            rows.add(Row.create(cells));
        }
        return rows;
    }

    private static Map<Integer, Cell> createCells(String board, SudokuSize size) {
        Map<Integer, Cell> boardCells = new HashMap<>();
        for (int i = 0; i < board.length(); i++) {
            int value = Integer.valueOf(String.valueOf(board.charAt(i)));
            if (value == 0) {
                boardCells.put(i, new Cell(size, Optional.empty()));
            } else {
                boardCells.put(i, new Cell(size, Optional.of(value)));
            }
        }
        return boardCells;
    }

    private static SudokuSize createSudokuSize(String board) {
        int size = calculateSize(board);
        Validate.isTrue(size*size == board.length(), "Sudoku input is not a square shaped board");

        return SudokuSize.create(size);
    }

    private static int calculateSize(String board) {
        int boardSize = board.length() + 1;
        return (int) Math.floor(Math.sqrt(boardSize));
    }
}
