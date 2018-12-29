package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class CellTest {

    private Set<Integer> validValues;

    @BeforeEach
    public void setUp() {
        //validValues = Set.of(6);
        validValues = new HashSet<>(Arrays.asList(6));
    }

    @Test
    public void emptyConstructorMethod() {
        Cell emptyCell = createEmptyCell();

        assertCellEmpty(emptyCell);
    }

    private void assertCellEmpty(Cell emptyCell) {
        assertThat(emptyCell.getValue()).isEmpty();
        assertThat(emptyCell.getPossibilities()).hasSameElementsAs(validValues);
    }

    @Test
    public void valueNotValid_throwsException() {
        assertThatThrownBy(() -> createCell(5))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> createCell(7))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void valueAndPossibilitiesSet_throwsException() {
        Cell.Builder cellBuilder = new Cell.Builder()
                .addValidValues(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .addPossibilities(1, 2, 4, 5, 6)
                .value(2);

        assertThatThrownBy(() -> cellBuilder.build())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void valueAndPossibilitiesBothEmpty_throwsException() {
        Cell.Builder cellBuilder = new Cell.Builder()
                .addValidValues(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .addPossibilities();

        assertThatThrownBy(() -> cellBuilder.build())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void validValue() {
        assertThatCellContainsValue(createCell(6), 6);
    }

    private void assertThatCellContainsValue(Cell cell, int value) {
        assertThat(cell.getValue()).contains(value);
        assertThat(cell.getPossibilities()).isEmpty();
    }

    private Cell createCell(int value) {
        return new Cell.Builder()
                .addAllValidValues(validValues)
                .value(value)
                .build();
    }

    private Cell createEmptyCell() {
        return new Cell.Builder()
                .addAllValidValues(validValues)
                .build();
    }
}