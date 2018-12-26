package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class CellTest {

    private Position position;

    @BeforeEach
    public void setUp() {
        position.of(1, 1);
    }

    @Test
    public void emptyCreation() {
        Cell cell = new Cell(position);

        assertThat(cell.getPossibilities()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThat(cell.getValue()).isEmpty();
    }

    @Test
    public void negativeValue() {
        assertThatThrownBy(() -> new Cell(-1, position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void zeroValue() {
        assertThatThrownBy(() -> new Cell(0, position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void tooBigValue() {
        assertThatThrownBy(() -> new Cell(10, position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void validValues() {
        IntStream.rangeClosed(1, 9)
                .forEach(value -> assertCellIsValid(new Cell(value, position), value));
    }

    private void assertCellIsValid(Cell cell, int value) {
        assertThat(cell.getValue()).contains(value);
        assertThat(cell.getPossibilities()).isEmpty();
    }

    @Test
    public void removePossibility() {
        Cell cell = new Cell(position);

        cell.removePossibility(5);

        assertThat(cell.getPossibilities()).containsExactly(1, 2, 3, 4, 6, 7, 8, 9);
        assertThat(cell.getPossibilities()).doesNotContain(5);
    }



}