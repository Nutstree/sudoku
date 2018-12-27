package nl.nutstree.sudoku.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private Cell[][] cells;
    private Map<Position, Cell> cellMap = new LinkedHashMap<>();
    private int size = 9;

    public Board() {
        createEmptyBoard();
    }

    private void createEmptyBoard() {
        cells = new Cell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Position position = Position.of(x, y);
                Cell cell = new Cell.Builder()
                        .position(position)
                        .build();

                cells[x][y] = cell;
                cellMap.put(position, cell);
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
        removePossibilityFromQ(value, position);
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

    private void removePossibilityFromQ(int value, Position position) {
        Collection<Cell> cellsInQuadrant = getCellsInQuadrant(position);

        cellsInQuadrant.stream()
                .map(cell -> cell.getPosition())
                .forEach(pos -> removePossibility(value, pos));
    }

    public void removePossibility(int possibility, Position position) {
        Cell cell = cells[position.getX()][position.getY()];
        Set<Integer> possibilities = new HashSet<>(cell.getPossibilities());

        possibilities.remove(possibility);
        Cell newCell = cell.withPossibilities(possibilities);

        cells[position.getX()][position.getY()] = newCell;
        cellMap.put(position, newCell);
    }

    Cell getCell(Position position) {
        return cells[position.getX()][position.getY()];
    }


    public Collection<Cell> getCellsInQuadrant(Position position) {
        int quadrant = position.getQuadrant();
        return cellMap.entrySet().stream()
                .filter(e -> e.getKey().getQuadrant() == quadrant)
                .map(e -> e.getValue())
                .collect(Collectors.toSet());
    }
}
