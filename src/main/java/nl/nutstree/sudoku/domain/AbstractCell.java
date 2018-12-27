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

    abstract Optional<Integer> getValue();
    abstract Position getPosition();

    @Value.Default
    public Set<Integer> getPossibilities() {
        if (getValue().isPresent()) {
            return Collections.emptySet();
        }

        return getDefaultPossibilities(9);
    }

    @Value.Check
    void validate() {
        getValue().ifPresent(value -> validateValue(value));
        getPossibilities().stream()
                .forEach(this::validatePossibility);
    }

    void validateValue(int value) {
        Validate.isTrue(isValidNumber(value), INVALID_VALUE, value);
    }

    void validatePossibility(int possibility) {
        Validate.isTrue(isValidNumber(possibility), INVALID_POSSIBILITY, possibility);
    }

    boolean isValidNumber(int number) {
        return number > 0 && number <= 9;
    }

    static Set<Integer> getDefaultPossibilities(int maxPosibilities) {
        return IntStream.rangeClosed(1, maxPosibilities)
                .boxed()
                .collect(Collectors.toSet());
    }
}
