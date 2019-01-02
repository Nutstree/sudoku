package nl.nutstree.sudoku.domain.autovalue;

import nl.nutstree.sudoku.domain.Location;
import nl.nutstree.sudoku.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SquareBoardTest {

    private SquareBoard board;

    @BeforeEach
    public void setUp() {
        board = SquareBoard.Factory.empty(Type.SQUARE_9X9);
    }

    @Test
    public void emptyBoard() {
        assertBoardEmpty(board);
    }

    private void assertBoardEmpty(SquareBoard board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 1; column < board.getSize(); column++) {
                Location location = ImmutableLocation.of(row, column);

                assertThatLocationIsUntouched(location);
            }
        }
    }

    private void assertThatLocationIsUntouched(Location location) {
        assertThat(board.getValue(location)).isEmpty();
        assertThat(board.getPossibilities(location)).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void constructBoardWithSudokuPuzzle_nonZeroFieldsCorrectlyFilled() {
        board = SquareBoard.Factory.of("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        // 010020300
        assertThat(board.getValue(ImmutableLocation.of(1, 0))).contains(1);
        assertThat(board.getValue(ImmutableLocation.of(4, 0))).contains(2);
        assertThat(board.getValue(ImmutableLocation.of(6, 0))).contains(3);

        // 004005060
        assertThat(board.getValue(ImmutableLocation.of(2, 1))).contains(4);
        assertThat(board.getValue(ImmutableLocation.of(5, 1))).contains(5);
        assertThat(board.getValue(ImmutableLocation.of(7, 1))).contains(6);

        // 070000008
        assertThat(board.getValue(ImmutableLocation.of(1, 2))).contains(7);
        assertThat(board.getValue(ImmutableLocation.of(8, 2))).contains(8);

        // 006900070
        assertThat(board.getValue(ImmutableLocation.of(2, 3))).contains(6);
        assertThat(board.getValue(ImmutableLocation.of(3, 3))).contains(9);
        assertThat(board.getValue(ImmutableLocation.of(7, 3))).contains(7);

        // 000100002
        assertThat(board.getValue(ImmutableLocation.of(3, 4))).contains(1);
        assertThat(board.getValue(ImmutableLocation.of(8, 4))).contains(2);

        // 030048000
        assertThat(board.getValue(ImmutableLocation.of(1, 5))).contains(3);
        assertThat(board.getValue(ImmutableLocation.of(4, 5))).contains(4);
        assertThat(board.getValue(ImmutableLocation.of(5, 5))).contains(8);

        // 500006040
        assertThat(board.getValue(ImmutableLocation.of(0, 6))).contains(5);
        assertThat(board.getValue(ImmutableLocation.of(5, 6))).contains(6);
        assertThat(board.getValue(ImmutableLocation.of(7, 6))).contains(4);

        // 000800106
        assertThat(board.getValue(ImmutableLocation.of(3, 7))).contains(8);
        assertThat(board.getValue(ImmutableLocation.of(6, 7))).contains(1);
        assertThat(board.getValue(ImmutableLocation.of(8, 7))).contains(6);

        // 008000000
        assertThat(board.getValue(ImmutableLocation.of(2, 8))).contains(8);
    }

    @Test
    public void constructBoardWithSudokuPuzzle_zeroFieldsStillEmpty() {
        board = SquareBoard.Factory.of("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        // random pick to check if fields are left empty
        assertThat(board.getValue(ImmutableLocation.of(0, 0))).isEmpty();
        assertThat(board.getValue(ImmutableLocation.of(6, 1))).isEmpty();
        assertThat(board.getValue(ImmutableLocation.of(3, 2))).isEmpty();
        assertThat(board.getValue(ImmutableLocation.of(8, 3))).isEmpty();
        assertThat(board.getValue(ImmutableLocation.of(2, 4))).isEmpty();
        assertThat(board.getValue(ImmutableLocation.of(7, 5))).isEmpty();
        assertThat(board.getValue(ImmutableLocation.of(1, 6))).isEmpty();
        assertThat(board.getValue(ImmutableLocation.of(4, 7))).isEmpty();
        assertThat(board.getValue(ImmutableLocation.of(5, 8))).isEmpty();
    }

    @Test
    public void toStringTest2() {
        String expected = "Sudoku Board: \n" +
                "[ ][1][ ]  [ ][2][ ]  [3][ ][ ]\n" +
                "[ ][ ][4]  [ ][ ][5]  [ ][6][ ]\n" +
                "[ ][7][ ]  [ ][ ][ ]  [ ][ ][8]\n" +
                "\n" +
                "[ ][ ][6]  [9][ ][ ]  [ ][7][ ]\n" +
                "[ ][ ][ ]  [1][ ][ ]  [ ][ ][2]\n" +
                "[ ][3][ ]  [ ][4][8]  [ ][ ][ ]\n" +
                "\n" +
                "[5][ ][ ]  [ ][ ][6]  [ ][4][ ]\n" +
                "[ ][ ][ ]  [8][ ][ ]  [1][ ][6]\n" +
                "[ ][ ][8]  [ ][ ][ ]  [ ][ ][ ]\n";
        board = SquareBoard.Factory.of("010020300004005060070000008006900070000100002030048000500006040000800106008000000");
        System.out.println(board);

        assertThat(board.toString()).isEqualTo(expected);
    }

    @Test
    public void setIllegalValues_throwsExeption() {
        assertThatThrownBy(() -> board.setValue(0, ImmutableLocation.of(0, 0)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> board.setValue(-1, ImmutableLocation.of(2, 8)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> board.setValue(10, ImmutableLocation.of(4, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void setValueWithInvalidLocation_throwsException() {
        Location invalidLocation = ImmutableLocation.of(Type.SQUARE_4X4, 0, 0);

        assertThatThrownBy(() -> board.setValue(0, invalidLocation))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void setLegalValues_containsValueAfterSetting() {
        assertSetValueCorrect(2);
        assertSetValueCorrect(5);
        assertSetValueCorrect(8);
        assertSetValueCorrect(3);
    }

    private void assertSetValueCorrect(int value) {
        Location location = ImmutableLocation.of(6, 6);
        board.setValue(value, location);

        assertThat(board.getValue(location)).contains(value);
    }

    @Test
    public void afterSettingValueOnlocation_relatedCellsPossibilityAdjusted() {
        Location location = ImmutableLocation.of(7, 7);
        Collection<Location> relatedLocations = board.getLocations().getRelatedLocations(location);
        Collection<Location> unrelatedLocations = getUnrelatedLocations(relatedLocations);

        board.setValue(5, location);

        assertLocationsDoesNotContainPossibility(relatedLocations, 5);
        assertOtherLocationsNotChanged(unrelatedLocations);
    }

    private void assertLocationsDoesNotContainPossibility(Collection<Location> locations, int possibility) {
        locations.stream()
                .forEach(location -> assertLocationDoesNotContainPossibility(location, possibility));
    }

    private void assertLocationDoesNotContainPossibility(Location location, int possibility) {
        Collection<Integer> possibilities = board.getPossibilities(location);

        assertThat(possibilities).doesNotContain(possibility);
    }

    private void assertOtherLocationsNotChanged(Collection<Location> unrelatedLocations) {
        unrelatedLocations.stream()
                .forEach(this::assertThatLocationIsUntouched);
    }

    private Collection<Location> getUnrelatedLocations(Collection<Location> relatedLocations) {
        Collection<Location> locations = new HashSet<Location>(board.getLocations().getAllLocations());
        locations.removeAll(relatedLocations);

        return locations;
    }
}