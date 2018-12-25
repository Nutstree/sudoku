package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class CellTest {

    @Test
    public void emptyCreation() {
        Cell cell = new Cell();

        assertThat(cell.getPossibilities()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThat(cell.getValue()).isEmpty();
    }

    @Test
    public void negativeValue() {
        assertThatThrownBy(() -> new Cell(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void zeroValue() {
        assertThatThrownBy(() -> new Cell(0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void tooBigValue() {
        assertThatThrownBy(() -> new Cell(10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void validValues() {
        IntStream.rangeClosed(1, 9)
                .forEach(value -> assertCellIsValid(new Cell(value), value));
    }

    private void assertCellIsValid(Cell cell, int value) {
        assertThat(cell.getValue()).contains(value);
        assertThat(cell.getPossibilities()).isEmpty();
    }

    @Test
    public void removePossibility() {
        Cell cell = new Cell();

        cell.removePossibility(5);

        assertThat(cell.getPossibilities()).containsExactly(1, 2, 3, 4, 6, 7, 8, 9);
        assertThat(cell.getPossibilities()).doesNotContain(5);
    }



}