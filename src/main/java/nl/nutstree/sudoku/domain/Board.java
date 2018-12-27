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
                cells[x][y] = new Cell.Builder()
                .position(Position.of(x, y))
                .build();
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
        Cell oldCell = cells[position.getX()][position.getY()];
        Cell newCell = oldCell.withValue(value).withPossibilities();

        cells[position.getX()][position.getY()] = newCell;

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
        Cell cell = cells[position.getX()][position.getY()];
        Set<Integer> possibilities = new HashSet<>(cell.getPossibilities());

        possibilities.remove(value);
        Cell newCell = cell.withPossibilities(possibilities);

        cells[position.getX()][position.getY()] = newCell;
    }

    Cell getCell(Position position) {
        return cells[position.getX()][position.getY()];
    }
}
