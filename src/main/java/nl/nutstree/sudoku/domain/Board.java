package nl.nutstree.sudoku.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private final Map<Location, Cell> cellMap;
    private static final int SIZE = 9;

    public Board() {
        cellMap = new LinkedHashMap<>();
        fillBoardWithEmptyCells();
    }

    public Board(String board) {
        cellMap = new LinkedHashMap<>();
        fillBoard(board);
    }

    private void fillBoardWithEmptyCells() {
        String emptyPuzzle = createEmptyPuzzleString();
        fillBoard(emptyPuzzle);
    }

    private String createEmptyPuzzleString() {
        return IntStream.range(0, SIZE * SIZE)
                .mapToObj(i -> "0")
                .collect(Collectors.joining());
    }

    private void fillBoard(String puzzle) {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                Location location = Location.of(x, y);
                int value = getValue(puzzle, x + (y * SIZE));

                Cell cell = value == 0 ? createEmptyCell(location) : createCell(value, location);

                cellMap.put(location, cell);
            }
        }
    }

    private int getValue(String puzzle, int stringPosition) {
        return Integer.valueOf(puzzle.substring(stringPosition, stringPosition + 1));
    }

    private Cell createEmptyCell(Location location) {
        return new Cell.Builder()
                .location(location)
                .build();
    }

    private Cell createCell(int value, Location location) {
        return new Cell.Builder()
                .value(value)
                .location(location)
                .build();
    }

    public int getSize() {
        return SIZE;
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

    private void removePossibility(int possibility, Location location) {
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
