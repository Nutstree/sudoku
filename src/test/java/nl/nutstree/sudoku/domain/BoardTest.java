package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    private Board board;
    private Position position;

    @BeforeEach
    public void setUp() {
        board = new Board();
        position = Position.of(0, 0);

    }

    @Test
    public void emptyBoard() {
        assertBoardEmpty(board);
    }

    private void assertBoardEmpty(Board board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 1; column < board.getSize(); column++) {
                Position position = Position.of(row, column);

                assertThat(board.getValue(position)).isEmpty();
                assertThat(board.getPossibilities(position)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
            }
        }
    }

    @Test
    public void setIllegalValues_throwsExeption() {
        assertThatThrownBy(() -> board.setValue(0, position))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> board.setValue(-1, position))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> board.setValue(10, position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void setLegalValues_containsValueAfterSetting() {
        IntStream.rangeClosed(1, 9)
        .forEach(this::assertSetValueCorrect);
    }

    private void assertSetValueCorrect(int value) {
        board.setValue(value, position);

        assertThat(board.getValue(position)).contains(value);
        assertThat(board.getPossibilities(position)).isEmpty();
    }

    @Test
    public void afterSettingValueOnPosition_relatedCellsPossibilityAdjusted() {
        board.setValue(5, position);

        assertRowDoesNotContainPossibility(5, position);
        assertColumnDoesNotContainPossibility(5, position);
        //TODO assert Quadrant does not contain possibility
        assertQDoesNotContainPossibility(5, position);
    }

    private void assertRowDoesNotContainPossibility(int value, Position position) {
        IntStream.range(0, 9)
                .filter(i -> position.getY() != i)
                .mapToObj(y -> Position.of(position.getX(), y))
                .forEach(pos -> assertValueNotAPossibilityAtPosition(value, pos));
    }

    private void assertColumnDoesNotContainPossibility(int value, Position position) {
        IntStream.range(0, 9)
                .filter(i -> position.getX() != i)
                .mapToObj(x -> Position.of(x, position.getY()))
                .forEach(pos -> assertValueNotAPossibilityAtPosition(value, pos));
    }

    private void assertQDoesNotContainPossibility(int possibility, Position position) {
        Collection<Cell> cellsInQuadrant = board.getCellsInQuadrant(position);

        assertCollectionDoesNotContainPossibility(cellsInQuadrant, possibility);
    }

    private void assertCollectionDoesNotContainPossibility(Collection<Cell> cellsInQuadrant, int possibility) {
        cellsInQuadrant.stream()
                .forEach(cell -> assertPossiblityNotInCell(cell, possibility));
    }

    private void assertPossiblityNotInCell(Cell cell, int possibility) {
        assertThat(cell.getPossibilities()).doesNotContain(possibility);
    }

    private void assertValueNotAPossibilityAtPosition(int value, Position position) {
        Cell cell = board.getCell(position);
        Cell expected = new Cell.Builder()
                .position(position)
                .possibilities(generateNumberSetWithout(value))
                .build();

        assertThat(cell).isEqualTo(expected);
        assertThat(cell.getPossibilities()).doesNotContain(value);
        assertThat(cell.getPossibilities()).hasSize(8);
    }

    private Set<Integer> generateNumberSetWithout(int value) {
        return IntStream.rangeClosed(1, 9)
                .filter(i -> value != i)
                .boxed()
                .collect(Collectors.toSet());
    }


}