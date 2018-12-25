package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

@Value.Immutable
public interface Position {
    @Value.Parameter
    int row();
    @Value.Parameter
    int column();

    @Value.Check
    default void check() {
        Validate.exclusiveBetween(0, 10, row(), "Illegal row argument");
        Validate.exclusiveBetween(0, 10, column(),"Illegal column argument");
    }

}
