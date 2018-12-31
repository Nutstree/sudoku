package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

@Value.Immutable
abstract class AbstractLocation implements Location {
    static final String ILLEGAL_ROW_NUMBER = "Illegal row number: %s";
    static final String ILLEGAL_COLUMN_NUMBER = "Illegal column number: %s";

    @Value.Default
    public Type getBoardType() {
        return Type.SQUARE_9X9;
    }

    @Value.Parameter
    public abstract int getRow();

    @Value.Parameter
    public abstract int getColumn();

    @Value.Derived
    public int getQuadrant() {
        final int quadrantSize = getQuadrantSize();
        return ((getColumn() / quadrantSize) * quadrantSize) + (getRow() / quadrantSize);
    }

    private int getQuadrantSize() {
        return (int) Math.sqrt(getBoardType().getSize());
    }

    @Value.Check
    public void validate() {
        int maxSize = getBoardType().getSize() - 1;

        Validate.inclusiveBetween(0, maxSize, getRow(), ILLEGAL_ROW_NUMBER + getRow());
        Validate.inclusiveBetween(0, maxSize, getColumn(), ILLEGAL_COLUMN_NUMBER + getColumn());

    }

    public static Location of(Type type, int row, int column) {
        return new ImmutableLocation.Builder()
                .boardType(type)
                .row(row)
                .column(column)
                .build();
    }
}
