package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value.Immutable
abstract class AbstractCell {

    static final String INVALID_VALUE = "Invalid value: ";

    /**
     * helper factory method
     *
     * @return Cell without a value
     */
    @Value.Auxiliary
    public static Cell empty() {
        return new Cell.Builder()
                .value(0)
                .build();
    }

    @Value.Parameter
    abstract int getValue();

    @Value.Default
    public Set<Integer> getPossibilities() {
        if (getValue() != 0) {
            return Collections.emptySet();
        }

        return getDefaultPossibilities(9);
    }

    private static Set<Integer> getDefaultPossibilities(int maxPosibilities) {
        return IntStream.rangeClosed(1, maxPosibilities)
                .boxed()
                .collect(Collectors.toSet());
    }

    //TODO hmm invalid value is determined by board size...
    @Value.Check
    void validate() {
        Validate.inclusiveBetween(0, 9, getValue(), INVALID_VALUE, getValue());
    }
}
