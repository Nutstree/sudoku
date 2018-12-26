package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.Test;

import static nl.nutstree.sudoku.domain.AbstractPosition.ILLEGAL_X;
import static nl.nutstree.sudoku.domain.AbstractPosition.ILLEGAL_Y;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AbstractPositionTest {

    @Test
    public void illegalRow() {
        assertThatThrownBy(() -> Position.of(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ILLEGAL_X);

        assertThatThrownBy(() -> Position.of(9, 8))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ILLEGAL_X);
    }

    @Test
    public void illegalColumn() {
        assertThatThrownBy(() -> Position.of(0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ILLEGAL_Y);

        assertThatThrownBy(() -> Position.of(8, 9))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ILLEGAL_Y);
    }

}