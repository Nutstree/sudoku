package nl.nutstree.sudoku.domain.autovalue;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.Validate;

import java.util.Optional;

@AutoValue
public abstract class ImmutableCell implements Cell {

    static final String INVALID_VALUE = "Invalid value: %s";
    static final String INVALID_POSSIBILITY = "Invalid possibility: %s";
    static final String INVALID_STATE_BOTH_FILLED = "Value and possibilities can't co-exist within Cell: %s";

    public abstract Type getBoardType();

    public abstract Optional<Integer> getValue();

    public abstract ImmutableSet<Integer> getPossibilities();

    private void validate() {
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

    public static Builder builder() {
        return new AutoValue_ImmutableCell.Builder()
                .boardType(Type.SQUARE_9X9)
                .possibilities(ImmutableSet.of());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder boardType(Type type);

        public abstract Builder value(int value);

        public abstract Builder possibilities(ImmutableSet<Integer> possibilities);

        public abstract Builder possibilities(Integer... possibilities);

        abstract Type getBoardType();

        abstract Optional<Integer> getValue();

        abstract ImmutableSet<Integer> getPossibilities();

        abstract ImmutableCell autoBuild();  // not public

        public Cell build() {
            if (getValue().isEmpty() && getPossibilities().isEmpty()) {
                possibilities(getBoardType().getValidValues());
            }

            ImmutableCell cell = autoBuild();
            cell.validate();

            return cell;
        }
    }
}
