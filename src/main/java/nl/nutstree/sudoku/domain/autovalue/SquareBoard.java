package nl.nutstree.sudoku.domain.autovalue;

import com.google.auto.value.AutoValue;
import nl.nutstree.sudoku.domain.Location;
import nl.nutstree.sudoku.domain.Locations;
import nl.nutstree.sudoku.domain.Type;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AutoValue
public abstract class SquareBoard implements Board {
    public static final String INVALID_LOCATION = "Location is made for a different board: %s, location: %s";
    static final String BOARD_NAME = "Sudoku Board";

    abstract Type getType();

    abstract Map<Location, ImmutableCell> getCellMap();

    public abstract Locations getLocations();

    public int getSize() {
        return getType().getSize();
    }

    private ImmutableCell getCell(Location location) {
        validateLocation(location);
        return getCellMap().get(location);
    }

    public Optional<Integer> getValue(Location location) {
        return getCell(location).getValue();
    }

    public Collection<Integer> getPossibilities(Location location) {
        return getCell(location).getPossibilities();
    }

    public SquareBoard setValue(int value, Location location) {
        ImmutableCell cellWithNewValue = getCell(location).withValue(value);
        Map<Location, ImmutableCell> newCells = new LinkedHashMap<>(getCellMap());

        newCells.putAll(withoutPossibility(value, getLocations().getRelatedLocations(location)));
        newCells.put(location, cellWithNewValue);

        return toBuilder()
                .cellMap(newCells)
                .build();
    }

    private Map<Location, ImmutableCell> withoutPossibility(int possibility, Collection<Location> locations) {
        return locations.stream()
                .collect(Collectors.toMap(location -> location,
                        location -> getCell(location).withoutPossibility(possibility)));
    }

    void validateLocation(Location location) {
        Type typeOfLocation = ((ImmutableLocation) location).getBoardType();
        Validate.isTrue(typeOfLocation == getType(), INVALID_LOCATION, getType(), location);
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

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_SquareBoard.Builder()
                .type(Type.SQUARE_9X9);
    }

    public static SquareBoard of(String puzzle) {
        return AutoValue_SquareBoard.builder()
                .sudokuString(puzzle)
                .build();
    }

    public static SquareBoard empty(Type type) {
        return AutoValue_SquareBoard.builder()
                .sudokuString(createEmptyPuzzleString(type))
                .build();
    }

    private static String createEmptyPuzzleString(Type type) {
        return IntStream.range(0, type.getSize() * type.getSize())
                .mapToObj(i -> "0")
                .collect(Collectors.joining());
    }


    private static ImmutableCell createEmptyCell(Type type) {
        return ImmutableCell.builder()
                .boardType(type)
                .build();
    }

    private static ImmutableCell createCell(Type type, int value) {
        return ImmutableCell.builder()
                .boardType(type)
                .value(value)
                .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        abstract Builder type(Type type);

        abstract Builder cellMap(Map<Location, ImmutableCell> cellMap);

        abstract Builder locations(Locations locations);

        public Builder sudokuString(String puzzle) {
            Type type = determineType(puzzle);
            return AutoValue_SquareBoard.builder()
                    .type(type)
                    .locations(ImmutableLocations.of(type))
                    .cellMap(createCellsFrom(puzzle));

        }

        private static Type determineType(String puzzle) {
            return Type.valueOf((int) Math.sqrt(puzzle.length()));
        }

        private static Map<Location, ImmutableCell> createCellsFrom(String puzzle) {
            Type type = determineType(puzzle);
            Map<Location, ImmutableCell> cellMap = new LinkedHashMap<>();

            for (int y = 0; y < type.getSize(); y++) {
                for (int x = 0; x < type.getSize(); x++) {
                    Location location = ImmutableLocation.of(x, y);
                    int value = getValueFromPuzzleString(puzzle, x + (y * type.getSize()));

                    ImmutableCell cell = value == 0 ? createEmptyCell(type) : createCell(type, value);

                    cellMap.put(location, cell);
                }
            }

            return Map.copyOf(cellMap);
        }

        private static int getValueFromPuzzleString(String puzzle, int stringPosition) {
            return Integer.valueOf(puzzle.substring(stringPosition, stringPosition + 1));
        }

        abstract SquareBoard autoBuild();  // not public

        public SquareBoard build() {
            SquareBoard board = autoBuild();
            //board.validate();

            return board;
        }
    }
}
