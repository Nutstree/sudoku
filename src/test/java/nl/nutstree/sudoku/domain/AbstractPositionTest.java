package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.Test;

import static nl.nutstree.sudoku.domain.AbstractPosition.ILLEGAL_X;
import static nl.nutstree.sudoku.domain.AbstractPosition.ILLEGAL_Y;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void testQuadrant() {
        assertThat(Position.of(0, 0).getQuadrant()).isEqualTo(0);
        assertThat(Position.of(1, 0).getQuadrant()).isEqualTo(0);
        assertThat(Position.of(2, 0).getQuadrant()).isEqualTo(0);
        assertThat(Position.of(3, 0).getQuadrant()).isEqualTo(1);
        assertThat(Position.of(4, 0).getQuadrant()).isEqualTo(1);
        assertThat(Position.of(5, 0).getQuadrant()).isEqualTo(1);
        assertThat(Position.of(6, 0).getQuadrant()).isEqualTo(2);
        assertThat(Position.of(7, 0).getQuadrant()).isEqualTo(2);
        assertThat(Position.of(8, 0).getQuadrant()).isEqualTo(2);

        assertThat(Position.of(0, 1).getQuadrant()).isEqualTo(0);
        assertThat(Position.of(0, 2).getQuadrant()).isEqualTo(0);
        assertThat(Position.of(0, 3).getQuadrant()).isEqualTo(3);
        assertThat(Position.of(0, 4).getQuadrant()).isEqualTo(3);
        assertThat(Position.of(0, 5).getQuadrant()).isEqualTo(3);
        assertThat(Position.of(0, 6).getQuadrant()).isEqualTo(6);
        assertThat(Position.of(0, 7).getQuadrant()).isEqualTo(6);
        assertThat(Position.of(0, 8).getQuadrant()).isEqualTo(6);

        assertThat(Position.of(1, 1).getQuadrant()).isEqualTo(0);
        assertThat(Position.of(2, 2).getQuadrant()).isEqualTo(0);
        assertThat(Position.of(3, 3).getQuadrant()).isEqualTo(4);
        assertThat(Position.of(4, 4).getQuadrant()).isEqualTo(4);
        assertThat(Position.of(5, 5).getQuadrant()).isEqualTo(4);
        assertThat(Position.of(6, 6).getQuadrant()).isEqualTo(8);
        assertThat(Position.of(7, 7).getQuadrant()).isEqualTo(8);
        assertThat(Position.of(8, 8).getQuadrant()).isEqualTo(8);

        assertThat(Position.of(4, 1).getQuadrant()).isEqualTo(1);
        assertThat(Position.of(5, 2).getQuadrant()).isEqualTo(1);
        assertThat(Position.of(6, 3).getQuadrant()).isEqualTo(5);
        assertThat(Position.of(7, 4).getQuadrant()).isEqualTo(5);
        assertThat(Position.of(8, 5).getQuadrant()).isEqualTo(5);
        assertThat(Position.of(1, 6).getQuadrant()).isEqualTo(6);
        assertThat(Position.of(2, 7).getQuadrant()).isEqualTo(6);
        assertThat(Position.of(3, 8).getQuadrant()).isEqualTo(7);

        assertThat(Position.of(7, 1).getQuadrant()).isEqualTo(2);
        assertThat(Position.of(8, 2).getQuadrant()).isEqualTo(2);
        assertThat(Position.of(1, 3).getQuadrant()).isEqualTo(3);
        assertThat(Position.of(2, 4).getQuadrant()).isEqualTo(3);
        assertThat(Position.of(3, 5).getQuadrant()).isEqualTo(4);
        assertThat(Position.of(4, 6).getQuadrant()).isEqualTo(7);
        assertThat(Position.of(5, 7).getQuadrant()).isEqualTo(7);
        assertThat(Position.of(6, 8).getQuadrant()).isEqualTo(8);

    }

}