package nl.nutstree.sudoku.domain;

import java.util.*;

public class Board {
    private Cell[][] cells;
    private int size = 9;

    public Board() {
        createCells();
    }

    private void createCells() {
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Optional<Integer> getValue(Position position) {
        return cells[position.getX()][position.getY()].getValue();
    }

    public Collection<Integer> getPossibilities(Position position) {
        return new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8 , 9));
    }

    public void setValue(Position position, int value) {
        cells[position.getX()][position.getY()] = new Cell(value);
    }
}
