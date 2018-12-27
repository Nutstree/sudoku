package nl.nutstree.sudoku.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private Map<Location, Cell> cellMap;
    private int size = 9;

    public Board() {
        createEmptyBoard();
    }

    private void createEmptyBoard() {
        cellMap = new LinkedHashMap<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location location = Location.of(x, y);
                Cell cell = new Cell.Builder()
                        .location(location)
                        .build();

                cellMap.put(location, cell);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Optional<Integer> getValue(Location location) {
        return cellMap.get(location).getValue();
    }

    public Collection<Integer> getPossibilities(Location location) {
        return cellMap.get(location).getPossibilities();
    }

    public void setValue(int value, Location location) {
        Cell oldCell = cellMap.get(location);
        Cell newCell = oldCell.withValue(value).withPossibilities();

        cellMap.put(location, newCell);

        removePossibilityFromX(value, location.getX());
        removePossibilityFromY(value, location.getY());
        removePossibilityFromQ(value, location);
    }

    private void removePossibilityFromX(int value, int x) {
        IntStream.range(0, 9)
                .mapToObj(y -> Location.of(x, y))
                .forEach(location -> removePossibility(value, location));
    }

    private void removePossibilityFromY(int value, int y) {
        IntStream.range(0, 9)
                .mapToObj(x -> Location.of(x, y))
                .forEach(location -> removePossibility(value, location));
    }

    private void removePossibilityFromQ(int value, Location location) {
        Collection<Cell> cellsInQuadrant = getCellsInQuadrant(location);

        cellsInQuadrant.stream()
                .map(Cell::getLocation)
                .forEach(pos -> removePossibility(value, pos));
    }

    public void removePossibility(int possibility, Location location) {
        Cell cell = cellMap.get(location);
        Set<Integer> possibilities = new HashSet<>(cell.getPossibilities());

        possibilities.remove(possibility);
        Cell newCell = cell.withPossibilities(possibilities);

        cellMap.put(location, newCell);
    }

    public Collection<Cell> getCellsInQuadrant(Location location) {
        int quadrant = location.getQuadrant();
        return cellMap.entrySet().stream()
                .filter(e -> e.getKey().getQuadrant() == quadrant)
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    public Collection<Cell> getCellsInRow(int x) {
        return cellMap.entrySet().stream()
                .filter(e -> e.getKey().getX() == x)
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    public Collection<Cell> getCellsInColumn(int y) {
        return cellMap.entrySet().stream()
                .filter(e -> e.getKey().getY() == y)
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }
}
