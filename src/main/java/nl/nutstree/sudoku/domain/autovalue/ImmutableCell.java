package nl.nutstree.sudoku.domain.autovalue;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AutoValue
public abstract class ImmutableCell implements Cell {

    static final String INVALID_VALUE = "Invalid value: %s";
    static final String INVALID_POSSIBILITY = "Invalid possibility: %s";
    static final String INVALID_STATE_BOTH_FILLED = "Value and possibilities can't co-exist within Cell: %s";

    public abstract Type getBoardType();

    public abstract Optional<Integer> getValue();

    public abstract Set<Integer> getPossibilities();

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

    public Cell withValue(int value) {
        return ImmutableCell.builder()
                .boardType(this.getBoardType())
                .possibilities(Collections.emptySet())
                .value(value)
                .build();
    }


    public Cell withoutPossibility(int possibility) {
        HashSet<Integer> possibilities = new HashSet<>(getPossibilities());
        possibilities.remove(possibility);

        return this.toBuilder()
                .possibilities(possibilities)
                .build();
    }

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_ImmutableCell.Builder()
                .boardType(Type.SQUARE_9X9)
                .possibilities(Set.of());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder boardType(Type type);

        public abstract Builder value(int value);

        public abstract Builder possibilities(Set<Integer> possibilities);

        // Needed to implement it myself as autovalue doesn't handle this well
        // When 'ImmutableSet' (guava) was used, it would be handled ok by autovalue
        public Builder possibilities(Integer... possibilities) {
            return possibilities(Set.of(possibilities));
        }

        abstract Type getBoardType();

        abstract Optional<Integer> getValue();

        abstract Set<Integer> getPossibilities();

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
