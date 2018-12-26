package nl.nutstree.sudoku.domain;

import java.util.*;
import java.util.stream.IntStream;

public class Board {
    private Cell[][] cells;
    private int size = 9;

    public Board() {
        createCells();
    }

    private void createCells() {
        cells = new Cell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                cells[x][y] = new Cell(Position.of(x, y));
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
        return cells[position.getX()][position.getY()].getPossibilities();
    }

    public void setValue(int value, Position position) {
        //TODO: cell should become an immutable...
        cells[position.getX()][position.getY()] = new Cell(value, position);
        removePossibilityFromX(value, position.getX());
        removePossibilityFromY(value, position.getY());
    }

    private void removePossibilityFromX(int value, int x) {
        IntStream.range(0, 9)
                .mapToObj(y -> Position.of(x, y))
                .forEach(position -> removePossibility(value, position));
    }

    private void removePossibilityFromY(int value, int y) {
        IntStream.range(0, 9)
                .mapToObj(x -> Position.of(x, y))
                .forEach(position -> removePossibility(value, position));
    }

    public void removePossibility(int value, Position position) {
        cells[position.getX()][position.getY()].removePossibility(value);
    }

    Cell getCell(Position position) {
        return cells[position.getX()][position.getY()];
    }
}
