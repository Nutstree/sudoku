package nl.nutstree.sudoku.domain;

import org.immutables.value.Value;

@Value.Immutable
abstract class AbstractLocation {

    @Value.Parameter
    public abstract int getRow();

    @Value.Parameter
    public abstract int getColumn();
}
