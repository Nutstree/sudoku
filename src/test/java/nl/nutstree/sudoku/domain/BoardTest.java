package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    public void emptyBoard() {
        Board board = new Board();

        assertBoardEmpty(board);
    }

    private void assertBoardEmpty(Board board) {
        for (int row = 1; row <= 9; row++) {
            for (int column = 1; column<= 9; column++) {
                ImmutablePosition position = ImmutablePosition.of(row, column);

                assertThat(board.getValue(position)).isEmpty();
                assertThat(board.getPossibilities(position)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
            }
        }
    }

    @Test
    public void setValue() {
        Board board = new Board();
        ImmutablePosition position = ImmutablePosition.of(1, 1);

        board.setValue(position, 5);

        assertThat(board.getValue(position)).contains(5);
    }

}