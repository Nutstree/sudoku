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
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 1; column < board.getSize(); column++) {
                Position position = Position.of(row, column);

                assertThat(board.getValue(position)).isEmpty();
                assertThat(board.getPossibilities(position)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
            }
        }
    }

    @Test
    public void setValue() {
        Board board = new Board();
        Position position = Position.of(1, 1);

        board.setValue(position, 5);

        assertThat(board.getValue(position)).contains(5);
    }

}