package nl.nutstree.sudoku.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private Map<Position, Cell> cellMap;
    private int size = 9;

    public Board() {
        createEmptyBoard();
    }

    private void createEmptyBoard() {
        cellMap = new LinkedHashMap<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Position position = Position.of(x, y);
                Cell cell = new Cell.Builder()
                        .position(position)
                        .build();

                cellMap.put(position, cell);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Optional<Integer> getValue(Position position) {
        return cellMap.get(position).getValue();
    }

    public Collection<Integer> getPossibilities(Position position) {
        return cellMap.get(position).getPossibilities();
    }

    public void setValue(int value, Position position) {
        Cell oldCell = cellMap.get(position);
        Cell newCell = oldCell.withValue(value).withPossibilities();

        cellMap.put(position, newCell);

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
        Cell cell = cellMap.get(position);
        Set<Integer> possibilities = new HashSet<>(cell.getPossibilities());

        possibilities.remove(possibility);
        Cell newCell = cell.withPossibilities(possibilities);

        cellMap.put(position, newCell);
    }

    Cell getCell(Position position) {
        return cellMap.get(position);
    }


    public Collection<Cell> getCellsInQuadrant(Position position) {
        int quadrant = position.getQuadrant();
        return cellMap.entrySet().stream()
                .filter(e -> e.getKey().getQuadrant() == quadrant)
                .map(e -> e.getValue())
                .collect(Collectors.toSet());
    }
}
