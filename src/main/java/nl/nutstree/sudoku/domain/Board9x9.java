package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board9x9 implements Board {
    private final Map<Location, Cell> cellMap;
    private static final int SIZE = 9;
    static final String BOARD_NAME = "Sudoku Board 9x9";
    static final String ILLEGAL_VALUE = "Illegal value: ";
    static final String ILLEGAL_LOCATION_ROW = "Illegal row for location: ";
    static final String ILLEGAL_LOCATION_COLUMN = "Illegal column for location: ";

    public Board9x9() {
        cellMap = new LinkedHashMap<>();
        fillBoardWithEmptyCells();
    }

    public Board9x9(String board) {
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
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Location location = Location.of(x, y);
                int value = getValue(puzzle, x + (y * SIZE));

                Cell cell = value == 0 ? Cell.empty() : Cell.of(value);

                cellMap.put(location, cell);
            }
        }
    }

    private int getValue(String puzzle, int stringPosition) {
        return Integer.valueOf(puzzle.substring(stringPosition, stringPosition + 1));
    }

    public int getSize() {
        return SIZE;
    }

    public Optional<Integer> getValue(Location location) {
        validateLocation(location);
        return cellMap.get(location).getValue();
    }

    public Collection<Integer> getPossibilities(Location location) {
        validateLocation(location);
        return cellMap.get(location).getPossibilities();
    }

    public void setValue(int value, Location location) {
        validateValue(value);
        validateLocation(location);
        Cell oldCell = cellMap.get(location);
        Cell newCell = oldCell.withValue(value).withPossibilities();

        cellMap.put(location, newCell);
        Collection<Location> relatedLocations = getRelatedLocations(location);
        removePossibility(value, relatedLocations);
    }

    private void removePossibility(int possibility, Collection<Location> locations) {
        locations.stream()
                .forEach(location -> removePossibility(possibility, location));
    }

    private void removePossibility(int possibility, Location location) {
        Cell cell = cellMap.get(location);
        Set<Integer> possibilities = new HashSet<>(cell.getPossibilities());

        possibilities.remove(possibility);
        Cell newCell = cell.withPossibilities(possibilities);

        cellMap.put(location, newCell);
    }

    public Collection<Location> getAllLocations() {
        return Collections.unmodifiableSet(cellMap.keySet());
    }

    public Collection<Location> getLocationsInRow(Location referenceLocation) {
        validateLocation(referenceLocation);

        int row = referenceLocation.getRow();
        return cellMap.keySet().stream()
                .filter(location -> location.getRow() == row)
                .collect(Collectors.toSet());
    }

    public Collection<Location> getLocationsInColumn(Location referenceLocation) {
        validateLocation(referenceLocation);

        int column = referenceLocation.getColumn();
        return cellMap.keySet().stream()
                .filter(location -> location.getColumn() == column)
                .collect(Collectors.toSet());
    }

    public Collection<Location> getLocationsInQuadrant(Location referenceLocation) {
        validateLocation(referenceLocation);

        int quadrant = getQuadrantOf(referenceLocation);
        return cellMap.keySet().stream()
                .filter(location -> getQuadrantOf(location) == quadrant)
                .collect(Collectors.toSet());
    }

    public Collection<Location> getRelatedLocations(Location referenceLocation) {
        validateLocation(referenceLocation);

        Collection<Location> relatedLocations = new HashSet<>();
        relatedLocations.addAll(getLocationsInRow(referenceLocation));
        relatedLocations.addAll(getLocationsInColumn(referenceLocation));
        relatedLocations.addAll(getLocationsInQuadrant(referenceLocation));

        return relatedLocations;
    }

    static int getQuadrantOf(Location referenceLocation) {
        final int quadrantSize = getQuadrantSize();
        return ((referenceLocation.getColumn() / quadrantSize) * quadrantSize) + (referenceLocation.getRow() / quadrantSize);
    }

    private static int getQuadrantSize() {
        return (int) Math.sqrt(SIZE);
    }

    void validateValue(int value) {
        Validate.inclusiveBetween(1, getSize(), value, ILLEGAL_VALUE + value);
    }

    void validateLocation(Location location) {
        Validate.inclusiveBetween(0, getSize() - 1L, location.getRow(), ILLEGAL_LOCATION_ROW + location.getRow());
        Validate.inclusiveBetween(0, getSize() - 1L, location.getColumn(), ILLEGAL_LOCATION_COLUMN + location.getColumn());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(BOARD_NAME + ": \n");

        int quadrantSize = getQuadrantSize();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (x != 0 && x % quadrantSize == 0) {
                    stringBuilder.append("  ");
                }
                if (y != 0 && x == 0 && y % quadrantSize == 0) {
                    stringBuilder.append("\n");
                }
                Optional<Integer> value = getValue(Location.of(x, y));
                String stringValue = value.isPresent() ? "[" + value.get() + "]" : "[ ]";
                stringBuilder.append(stringValue);
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
