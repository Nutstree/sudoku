package nl.nutstree.sudoku.domain.immutables;

import nl.nutstree.sudoku.domain.Cell;
import nl.nutstree.sudoku.domain.Type;
import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Value.Immutable
abstract class AbstractCell implements Cell {

    static final String INVALID_VALUE = "Invalid value: %s";
    static final String INVALID_POSSIBILITY = "Invalid possibility: %s";
    static final String INVALID_STATE_BOTH_FILLED = "Value and possibilities can't co-exist within Cell: %s";


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
        Validate.validState(getPossibilities().isEmpty(), INVALID_STATE_BOTH_FILLED, this);
    }

    private void validatePossibilities() {
        Validate.isTrue(getBoardType().getValidValues().containsAll(getPossibilities()), INVALID_POSSIBILITY, getPossibilities());
    }

    public ImmutableCell clear() {
        return new ImmutableCell.Builder().from(this)
                .possibilities(Collections.emptySet())
                .value(Optional.empty())
                .build();
    }
}
