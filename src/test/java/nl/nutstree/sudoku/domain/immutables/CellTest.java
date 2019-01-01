package nl.nutstree.sudoku.domain.immutables;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class CellTest {

    @Test
    public void defaultSudokuType_SQUARE9x9() {
        Cell cell = new ImmutableCell.Builder()
                .build();

        assertThat(((ImmutableCell) cell).getBoardType()).isEqualByComparingTo(Type.SQUARE_9X9);
    }

    @Test
    public void emptyConstructorMethod() {
        Type type = Type.SQUARE_9X9;
        Cell emptyCell = new ImmutableCell.Builder()
                .boardType(type)
                .build();

        assertThat(emptyCell.getValue()).isEmpty();
        assertThat(emptyCell.getPossibilities()).hasSameElementsAs(type.getValidValues());
    }

    @Test
    public void valueNotValid_throwsException() {
        assertThatThrownBy(() -> createCell4x4(5))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> createCell9x9(12))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void valueAndPossibilitiesSet_throwsException() {
        ImmutableCell.Builder cellBuilder = new ImmutableCell.Builder()
                .boardType(Type.SQUARE_9X9)
                .addPossibilities(1, 2, 4, 5, 6)
                .value(2);

        assertThatThrownBy(() -> cellBuilder.build())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void validPossibilities() {
        Cell cell = new ImmutableCell.Builder()
                .boardType(Type.SQUARE_9X9)
                .addPossibilities(9, 2, 4, 5, 6)
                .build();

        assertThat(cell.getPossibilities()).containsExactlyInAnyOrder(9, 2, 4, 5, 6);
        assertThat(cell.getValue()).isEmpty();
    }

    @Test
    public void invalidPossibilities() {
        ImmutableCell.Builder cellBuilder = new ImmutableCell.Builder()
                .boardType(Type.SQUARE_9X9)
                .addPossibilities(0, 2, 4, 5, 6);

        assertThatThrownBy(() -> cellBuilder.build())
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void validValue() {
        Type type = Type.SQUARE_9X9;
        Cell cell = new ImmutableCell.Builder()
                .boardType(type)
                .value(6)
                .build();

        assertThat(cell.getValue()).contains(6);
        assertThat(cell.getPossibilities()).isEmpty();
    }

    private Cell createCell4x4(int value) {
        return new ImmutableCell.Builder()
                .boardType(Type.SQUARE_4X4)
                .value(value)
                .build();
    }

    private Cell createCell9x9(int value) {
        return new ImmutableCell.Builder()
                .boardType(Type.SQUARE_9X9)
                .value(value)
                .build();
    }
}