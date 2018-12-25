package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionTest {

    @Test
    public void illegalRow() {
        assertThatThrownBy(() -> ImmutablePosition.of(-1, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("row");

        assertThatThrownBy(() -> ImmutablePosition.of(0, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("row");

        assertThatThrownBy(() -> ImmutablePosition.of(10, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("row");
    }

    @Test
    public void illegalColumn() {
        assertThatThrownBy(() -> ImmutablePosition.of(1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("column");

        assertThatThrownBy(() -> ImmutablePosition.of(1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("column");

        assertThatThrownBy(() -> ImmutablePosition.of(1, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("column");
    }

}