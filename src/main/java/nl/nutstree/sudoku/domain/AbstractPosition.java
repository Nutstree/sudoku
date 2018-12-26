package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

@Value.Immutable
abstract class AbstractPosition {

    static String ILLEGAL_X = "Illegal X argument: ";
    static String ILLEGAL_Y = "Illegal Y argument: ";

    @Value.Parameter
    abstract int getX();
    @Value.Parameter
    abstract int getY();

    @Value.Check
    void check() {
        Validate.inclusiveBetween(0, 8, getX(), ILLEGAL_X + getX());
        Validate.inclusiveBetween(0, 8, getY(), ILLEGAL_Y + getY());
    }

}