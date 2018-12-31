package nl.nutstree.sudoku.domain;

import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Value.Immutable
abstract class AbstractCell implements Cell {

    static final String INVALID_VALUE = "Invalid value: %s";
    static final String INVALID_POSSIBILITY = "Invalid possibility: %s";

    @Value.Default
    public Type getBoardType() {
        return Type.SQUARE_9X9;
    }

    public abstract Optional<Integer> getValue();

    @Value.Default
    public Set<Integer> getPossibilities() {
        if (getValue().isPresent()) {
            return Collections.emptySet();
        }

        return getBoardType().getValidValues();
    }


    @Value.Check
    void validate() {
        getValue().ifPresentOrElse(this::validateValue,
                this::validatePossibilities);
    }

    private void validateValue(int value) {
        Validate.isTrue(getBoardType().getValidValues().contains(value), INVALID_VALUE, value);
    }

    private void validatePossibilities() {
        Validate.isTrue(getBoardType().getValidValues().containsAll(getPossibilities()), INVALID_POSSIBILITY, getPossibilities());
    }
}
