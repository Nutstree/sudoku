package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Value.Immutable
abstract class AbstractCell {

    static final String INVALID_VALUE = "Invalid value: %s";
    static final String INVALID_POSSIBILITY = "Invalid possibility: %s";
    static final String INVALID_STATE_BOTH_FILLED = "Value and possibilities can't co-exist within Cell: %s";
    static final String INVALID_STATE_BOTH_EMPTY = "Value and possibilities can't both be empty: %s";

    public abstract Set<Integer> getValidValues();

    public abstract Optional<Integer> getValue();

    @Value.Default
    public Set<Integer> getPossibilities() {
        if (getValue().isPresent()) {
            return Collections.emptySet();
        }

        return getValidValues();
    }

    @Value.Check
    void validate() {
        //getValue().ifPresentOrElse(this::validateValue, this::validatePossibilities);
        if (getValue().isPresent()) {
            validateValue(getValue().get());
        } else {
            validatePossibilities();
        }
    }

    private void validateValue(int value) {
        Validate.isTrue(getValidValues().contains(value), INVALID_VALUE, value);
        Validate.validState(getPossibilities().isEmpty(), INVALID_STATE_BOTH_FILLED, this);
    }

    private void validatePossibilities() {
        Validate.isTrue(getValidValues().containsAll(getPossibilities()), INVALID_POSSIBILITY, getPossibilities());
        Validate.validState(!getPossibilities().isEmpty(), INVALID_STATE_BOTH_EMPTY, this);
    }
}
