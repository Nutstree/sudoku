package nl.nutstree.sudoku.domain.autovalue;

import com.google.auto.value.AutoValue;
import nl.nutstree.sudoku.domain.Location;
import nl.nutstree.sudoku.domain.Type;
import org.apache.commons.lang3.Validate;


@AutoValue
abstract class ImmutableLocation implements Location {
    static final String ILLEGAL_ROW_NUMBER = "Illegal row number: %s";
    static final String ILLEGAL_COLUMN_NUMBER = "Illegal column number: %s";

    public abstract Type getBoardType();

    public abstract int getRow();

    public abstract int getColumn();

    public int getQuadrant() {
        final int quadrantSize = getQuadrantSize();
        return ((getColumn() / quadrantSize) * quadrantSize) + (getRow() / quadrantSize);
    }

    private int getQuadrantSize() {
        return (int) Math.sqrt(getBoardType().getSize());
    }

    private void validate() {
        int maxSize = getBoardType().getSize() - 1;

        Validate.inclusiveBetween(0, maxSize, getRow(), ILLEGAL_ROW_NUMBER + getRow());
        Validate.inclusiveBetween(0, maxSize, getColumn(), ILLEGAL_COLUMN_NUMBER + getColumn());

    }

    public static Location of(Type type, int row, int column) {
        return ImmutableLocation.builder()
                .boardType(type)
                .row(row)
                .column(column)
                .build();
    }


    public static Location of(int row, int column) {
        return ImmutableLocation.builder()
                .row(row)
                .column(column)
                .build();
    }

    public static ImmutableLocation.Builder builder() {
        return new AutoValue_ImmutableLocation.Builder()
                .boardType(Type.SQUARE_9X9);
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder boardType(Type type);

        public abstract Builder row(int rowNumber);

        public abstract Builder column(int columnNumber);

        abstract ImmutableLocation autoBuild();  // not public

        public Location build() {
            ImmutableLocation location = autoBuild();
            location.validate();

            return location;
        }
    }
}
