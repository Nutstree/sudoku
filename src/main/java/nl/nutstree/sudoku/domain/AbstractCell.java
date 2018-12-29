package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value.Immutable
abstract class AbstractCell {

    static final String INVALID_VALUE = "Invalid value: ";
    static final String INVALID_POSSIBILITY = "Invalid possibility: ";

    public abstract Optional<Integer> getValue();

    @Value.Default
    public Set<Integer> getPossibilities() {
        if (getValue().isPresent()) {
            return Collections.emptySet();
        }

        return getDefaultPossibilities(9);
    }

    private static Set<Integer> getDefaultPossibilities(int maxPosibilities) {
        return IntStream.rangeClosed(1, maxPosibilities)
                .boxed()
                .collect(Collectors.toSet());
    }

    /**
     * helper factory method
     *
     * @return Cell without a value
     */
    @Value.Auxiliary
    public static Cell empty() {
        return new Cell.Builder()
                .build();
    }

    /**
     * helper factory method
     *
     * @return Cell without a value
     */
    @Value.Auxiliary
    public static Cell of(int value) {
        return new Cell.Builder()
                .value(value)
                .build();
    }

    @Value.Check
    void validate() {
        getValue().ifPresent(this::validateValue);
        getPossibilities().stream()
                .forEach(this::validatePossibility);
    }

    private void validateValue(int value) {
        Validate.isTrue(isValidNumber(value), INVALID_VALUE, value);
    }

    private void validatePossibility(int possibility) {
        Validate.isTrue(isValidNumber(possibility), INVALID_POSSIBILITY, possibility);
    }

    private boolean isValidNumber(int number) {
        return number > 0 && number <= 9;
    }
}
