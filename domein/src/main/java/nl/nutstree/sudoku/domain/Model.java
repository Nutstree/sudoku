package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;

import java.util.*;

public class Model {

    private SudokuSize size;
    private List<Row> rows;
    private List<Column> columns;
    private List<Square> squares;

    Model(SudokuSize size, List<Row> rows, List<Column> columns, List<Square> squares) {
        this.size = size;
        this.rows = rows;
        this.columns = columns;
        this.squares = squares;
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Square> getSquares() {
        return squares;
    }


}
