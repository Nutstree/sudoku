package nl.nutstree.sudoku.domain.autovalue;

import nl.nutstree.sudoku.domain.*;
import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SquareBoard implements Board {
    public static final String INVALID_LOCATION = "Location is made for a different board: %s, location: %s";
    private final Map<Location, Cell> cellMap;
    private final Type type;
    static final String BOARD_NAME = "Sudoku Board";

    private SquareBoard(Type type, Map<Location, Cell> cellMap) {
        this.type = type;
        this.cellMap = cellMap;
    }

    public int getSize() {
        return type.getSize();
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
        validateLocation(location);
        ImmutableCell oldCell = (ImmutableCell) cellMap.get(location);

        Cell newCell = oldCell.withValue(value);

        cellMap.put(location, newCell);
        Collection<Location> relatedLocations = getRelatedLocations(location);
        removePossibility(value, relatedLocations);
    }

    private void removePossibility(int possibility, Collection<Location> locations) {
        locations.stream()
                .forEach(location -> removePossibility(possibility, location));
    }

    private void removePossibility(int possibility, Location location) {
        ImmutableCell cell = (ImmutableCell) cellMap.get(location);

        Cell newCell = cell.withoutPossibility(possibility);

        cellMap.put(location, newCell);
    }

    public Locations getLocations() {
        return ImmutableLocations.builder()
                .boardType(type)
                .allLocations(cellMap.keySet())
                .build();
    }

    public Collection<Location> getAllLocations() {
        return Collections.unmodifiableSet(cellMap.keySet());
    }

    public Collection<Location> getLocationsInSameRow(Location referenceLocation) {
        validateLocation(referenceLocation);

        int row = referenceLocation.getRow();
        return cellMap.keySet().stream()
                .filter(location -> location.getRow() == row)
                .collect(Collectors.toSet());
    }

    public Collection<Location> getLocationsInSameColumn(Location referenceLocation) {
        validateLocation(referenceLocation);

        int column = referenceLocation.getColumn();
        return cellMap.keySet().stream()
                .filter(location -> location.getColumn() == column)
                .collect(Collectors.toSet());
    }

    public Collection<Location> getLocationsInSameQuadrant(Location referenceLocation) {
        validateLocation(referenceLocation);

        int quadrant = referenceLocation.getQuadrant();
        return cellMap.keySet().stream()
                .filter(location -> location.getQuadrant() == quadrant)
                .collect(Collectors.toSet());
    }

    public Collection<Location> getRelatedLocations(Location referenceLocation) {
        validateLocation(referenceLocation);

        Collection<Location> relatedLocations = new HashSet<>();
        relatedLocations.addAll(getLocationsInSameRow(referenceLocation));
        relatedLocations.addAll(getLocationsInSameColumn(referenceLocation));
        relatedLocations.addAll(getLocationsInSameQuadrant(referenceLocation));

        return relatedLocations;
    }

    void validateLocation(Location location) {
        Type typeOfLocation = ((ImmutableLocation) location).getBoardType();
        Validate.isTrue(typeOfLocation == type, INVALID_LOCATION, type, location);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(BOARD_NAME + ": \n");

        int quadrantSize = (int) Math.sqrt(getSize());
        for (int y = 0; y < getSize(); y++) {
            for (int x = 0; x < getSize(); x++) {
                if (x != 0 && x % quadrantSize == 0) {
                    stringBuilder.append("  ");
                }
                if (y != 0 && x == 0 && y % quadrantSize == 0) {
                    stringBuilder.append("\n");
                }
                Optional<Integer> value = getValue(ImmutableLocation.of(x, y));
                String stringValue = value.isPresent() ? "[" + value.get() + "]" : "[ ]";
                stringBuilder.append(stringValue);
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public static class Factory {
        static Map<Location, Cell> cellMap;
        static Type type;

        public static SquareBoard empty(Type boardType) {
            type = boardType;
            cellMap = new LinkedHashMap<>();
            fillBoardWithEmptyCells();

            return new SquareBoard(type, cellMap);
        }

        public static SquareBoard of(String puzzle) {
            type = determineType(puzzle);
            cellMap = new LinkedHashMap<>();
            fillBoard(puzzle);

            return new SquareBoard(type, cellMap);
        }

        private static Type determineType(String puzzle) {
            return Type.valueOf((int) Math.sqrt(puzzle.length()));
        }

        private static void fillBoardWithEmptyCells() {
            String emptyPuzzle = createEmptyPuzzleString();
            fillBoard(emptyPuzzle);
        }

        private static String createEmptyPuzzleString() {
            return IntStream.range(0, type.getSize() * type.getSize())
                    .mapToObj(i -> "0")
                    .collect(Collectors.joining());
        }

        private static void fillBoard(String puzzle) {
            for (int y = 0; y < type.getSize(); y++) {
                for (int x = 0; x < type.getSize(); x++) {
                    Location location = ImmutableLocation.of(x, y);
                    int value = getValueFromPuzzleString(puzzle, x + (y * type.getSize()));

                    Cell cell = value == 0 ? createEmptyCell() : createCell(value);

                    cellMap.put(location, cell);
                }
            }
        }

        private static int getValueFromPuzzleString(String puzzle, int stringPosition) {
            return Integer.valueOf(puzzle.substring(stringPosition, stringPosition + 1));
        }

        private static Cell createEmptyCell() {
            return ImmutableCell.builder()
                    .boardType(type)
                    .build();
        }

        private static Cell createCell(int value) {
            return ImmutableCell.builder()
                    .boardType(type)
                    .value(value)
                    .build();
        }
    }
}
