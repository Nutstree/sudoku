package nl.nutstree.sudoku.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CellTest {

    private SudokuSize size;

    @Before
    public void setUp() {
        size = SudokuSize.create(9);
    }

    @Test
    public void valueNegative() {
        assertThatThrownBy(() -> new Cell(size, Optional.of(-1))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void valueTooBig() {
        assertThatThrownBy(() -> new Cell(size, Optional.of(11))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void valueZero() {
        assertThatThrownBy(() -> new Cell(size, Optional.of(0))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void noValueToString() {
        Cell cell = new Cell(size, Optional.empty());

        assertThat(cell.toString()).isEqualTo("{ }");
    }

    @Test
    public void valueToString() {
        Cell cell = new Cell(size, Optional.of(5));

        assertThat(cell.toString()).isEqualTo("{5}");
    }

    @Test
    public void overrideValueAlreadySet() {
        Cell cell = new Cell(size, Optional.of(5));

        assertThatThrownBy(() -> cell.setValue(8)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void setValueToBig() {
        Cell cell = new Cell(size, Optional.empty());

        assertThatThrownBy(() -> cell.setValue(19)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void setValueOk() {
        Cell cell = new Cell(size, Optional.empty());
        cell.setValue(7);

        assertThat(cell.getValue()).contains(7);
    }
}