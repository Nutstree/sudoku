package nl.nutstree.sudoku.domain;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.Validate;

/**
 * Value object representing size of sudoku
 */
@AutoValue
public abstract class SudokuSize {
    public abstract int getValue();

    public static SudokuSize create(int value) {
        Validate.isTrue(value > 0, "SudokuSize should be greater then 0");
        return new AutoValue_SudokuSize(value);
    }
}
