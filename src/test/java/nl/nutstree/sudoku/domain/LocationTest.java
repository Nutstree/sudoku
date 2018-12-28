package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.Test;

import static nl.nutstree.sudoku.domain.AbstractLocation.ILLEGAL_X;
import static nl.nutstree.sudoku.domain.AbstractLocation.ILLEGAL_Y;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocationTest {

    @Test
    public void illegalRow() {
        assertThatThrownBy(() -> Location.of(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ILLEGAL_X);

        assertThatThrownBy(() -> Location.of(9, 8))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ILLEGAL_X);
    }

    @Test
    public void illegalColumn() {
        assertThatThrownBy(() -> Location.of(0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ILLEGAL_Y);

        assertThatThrownBy(() -> Location.of(8, 9))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ILLEGAL_Y);
    }

    @Test
    public void testQuadrant() {
        assertThat(Location.of(0, 0).getQuadrant()).isEqualTo(0);
        assertThat(Location.of(1, 0).getQuadrant()).isEqualTo(0);
        assertThat(Location.of(2, 0).getQuadrant()).isEqualTo(0);
        assertThat(Location.of(3, 0).getQuadrant()).isEqualTo(1);
        assertThat(Location.of(4, 0).getQuadrant()).isEqualTo(1);
        assertThat(Location.of(5, 0).getQuadrant()).isEqualTo(1);
        assertThat(Location.of(6, 0).getQuadrant()).isEqualTo(2);
        assertThat(Location.of(7, 0).getQuadrant()).isEqualTo(2);
        assertThat(Location.of(8, 0).getQuadrant()).isEqualTo(2);

        assertThat(Location.of(0, 1).getQuadrant()).isEqualTo(0);
        assertThat(Location.of(0, 2).getQuadrant()).isEqualTo(0);
        assertThat(Location.of(0, 3).getQuadrant()).isEqualTo(3);
        assertThat(Location.of(0, 4).getQuadrant()).isEqualTo(3);
        assertThat(Location.of(0, 5).getQuadrant()).isEqualTo(3);
        assertThat(Location.of(0, 6).getQuadrant()).isEqualTo(6);
        assertThat(Location.of(0, 7).getQuadrant()).isEqualTo(6);
        assertThat(Location.of(0, 8).getQuadrant()).isEqualTo(6);

        assertThat(Location.of(1, 1).getQuadrant()).isEqualTo(0);
        assertThat(Location.of(2, 2).getQuadrant()).isEqualTo(0);
        assertThat(Location.of(3, 3).getQuadrant()).isEqualTo(4);
        assertThat(Location.of(4, 4).getQuadrant()).isEqualTo(4);
        assertThat(Location.of(5, 5).getQuadrant()).isEqualTo(4);
        assertThat(Location.of(6, 6).getQuadrant()).isEqualTo(8);
        assertThat(Location.of(7, 7).getQuadrant()).isEqualTo(8);
        assertThat(Location.of(8, 8).getQuadrant()).isEqualTo(8);

        assertThat(Location.of(4, 1).getQuadrant()).isEqualTo(1);
        assertThat(Location.of(5, 2).getQuadrant()).isEqualTo(1);
        assertThat(Location.of(6, 3).getQuadrant()).isEqualTo(5);
        assertThat(Location.of(7, 4).getQuadrant()).isEqualTo(5);
        assertThat(Location.of(8, 5).getQuadrant()).isEqualTo(5);
        assertThat(Location.of(1, 6).getQuadrant()).isEqualTo(6);
        assertThat(Location.of(2, 7).getQuadrant()).isEqualTo(6);
        assertThat(Location.of(3, 8).getQuadrant()).isEqualTo(7);

        assertThat(Location.of(7, 1).getQuadrant()).isEqualTo(2);
        assertThat(Location.of(8, 2).getQuadrant()).isEqualTo(2);
        assertThat(Location.of(1, 3).getQuadrant()).isEqualTo(3);
        assertThat(Location.of(2, 4).getQuadrant()).isEqualTo(3);
        assertThat(Location.of(3, 5).getQuadrant()).isEqualTo(4);
        assertThat(Location.of(4, 6).getQuadrant()).isEqualTo(7);
        assertThat(Location.of(5, 7).getQuadrant()).isEqualTo(7);
        assertThat(Location.of(6, 8).getQuadrant()).isEqualTo(8);

    }

}