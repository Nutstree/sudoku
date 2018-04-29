package nl.nutstree.sudoku.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SudokuSizeTest {

    @Test
    public void maxValueNegative() {
        assertThatThrownBy(() -> SudokuSize.create(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void  maxValueZero() {
        assertThatThrownBy(() -> SudokuSize.create(0)).isInstanceOf(IllegalArgumentException.class);
    }
}