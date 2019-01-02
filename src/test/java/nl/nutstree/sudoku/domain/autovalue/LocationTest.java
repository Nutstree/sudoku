package nl.nutstree.sudoku.domain.autovalue;

import nl.nutstree.sudoku.domain.Location;
import nl.nutstree.sudoku.domain.Type;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocationTest {

    @Test
    public void defaultSudokuType_SQUARE9x9() {
        Location location = ImmutableLocation.builder()
                .row(0)
                .column(0)
                .build();

        assertThat(((ImmutableLocation) location).getBoardType()).isEqualByComparingTo(Type.SQUARE_9X9);
    }

    @Test
    public void invalidRow_throwsException() {
        assertThatThrownBy(() -> ImmutableLocation.of(-1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("row");

        assertThatThrownBy(() -> ImmutableLocation.of(9, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("row");


    }

    @Test
    public void invalidColumnRow_throwsException() {
        assertThatThrownBy(() -> ImmutableLocation.of(0, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("column");
        assertThatThrownBy(() -> ImmutableLocation.of(0, 9))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("column");

    }

    @Test
    public void getQuadrant_4x4() {
        assertThat(ImmutableLocation.of(Type.SQUARE_4X4, 0, 0).getQuadrant()).isEqualTo(0);
        assertThat(ImmutableLocation.of(Type.SQUARE_4X4, 2, 0).getQuadrant()).isEqualTo(1);
        assertThat(ImmutableLocation.of(Type.SQUARE_4X4, 1, 3).getQuadrant()).isEqualTo(2);
        assertThat(ImmutableLocation.of(Type.SQUARE_4X4, 3, 3).getQuadrant()).isEqualTo(3);
    }

    @Test
    public void getQuadrant_9x9() {
        assertThat(ImmutableLocation.of(2, 0).getQuadrant()).isEqualTo(0);
        assertThat(ImmutableLocation.of(3, 1).getQuadrant()).isEqualTo(1);
        assertThat(ImmutableLocation.of(8, 2).getQuadrant()).isEqualTo(2);
        assertThat(ImmutableLocation.of(2, 4).getQuadrant()).isEqualTo(3);
        assertThat(ImmutableLocation.of(3, 5).getQuadrant()).isEqualTo(4);
        assertThat(ImmutableLocation.of(8, 5).getQuadrant()).isEqualTo(5);
        assertThat(ImmutableLocation.of(1, 6).getQuadrant()).isEqualTo(6);
        assertThat(ImmutableLocation.of(7, 7).getQuadrant()).isEqualTo(8);
        assertThat(ImmutableLocation.of(3, 8).getQuadrant()).isEqualTo(7);
    }

    @Test
    public void factoryMethod_equalToBuilder() {
        Location expected = ImmutableLocation.builder()
                .boardType(Type.SQUARE_9X9)
                .row(3)
                .column(4)
                .build();

        Location actual = ImmutableLocation.of(Type.SQUARE_9X9, 3, 4);

        assertThat(actual).isEqualTo(expected);
    }
}