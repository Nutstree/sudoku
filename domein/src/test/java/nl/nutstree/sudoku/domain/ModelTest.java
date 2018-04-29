package nl.nutstree.sudoku.domain;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ModelTest {

    /**
     * 010  020  300
     * 004  005  060
     * 070  000  008
     *
     * 006  900  070
     * 000  100  002
     * 030  048  000
     *
     * 500  006  040
     * 000  800  106
     * 008  000  000
     */
    @Test
    public void testHorizontals_create_common9x9Sudoku() {
        Model model = ModelBuilder.create("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        List<Row> rows = model.getRows();
        assertThat(rows).hasSize(9);
        assertThat(rows.get(0).getValues()).containsExactly(1,2,3);
        assertThat(rows.get(1).getValues()).containsExactly(4,5,6);
        assertThat(rows.get(2).getValues()).containsExactly(7,8);
        assertThat(rows.get(3).getValues()).containsExactly(6,9,7);
        assertThat(rows.get(4).getValues()).containsExactly(1,2);
        assertThat(rows.get(5).getValues()).containsExactly(3,4,8);
        assertThat(rows.get(6).getValues()).containsExactly(5,6,4);
        assertThat(rows.get(7).getValues()).containsExactly(8,1,6);
        assertThat(rows.get(8).getValues()).containsExactly(8);
    }

    /**
     * 010  020  300
     * 004  005  060
     * 070  000  008
     *
     * 006  900  070
     * 000  100  002
     * 030  048  000
     *
     * 500  006  040
     * 000  800  106
     * 008  000  000
     */
    @Test
    public void testVerticals_create_common9x9Sudoku() {
        Model model = ModelBuilder.create("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        List<Column> columns = model.getColumns();
        assertThat(columns).hasSize(9);
        assertThat(columns.get(0).getValues()).containsExactly(5);
        assertThat(columns.get(1).getValues()).containsExactly(1,7,3);
        assertThat(columns.get(2).getValues()).containsExactly(4,6,8);
        assertThat(columns.get(3).getValues()).containsExactly(9,1,8);
        assertThat(columns.get(4).getValues()).containsExactly(2,4);
        assertThat(columns.get(5).getValues()).containsExactly(5,8,6);
        assertThat(columns.get(6).getValues()).containsExactly(3,1);
        assertThat(columns.get(7).getValues()).containsExactly(6,7,4);
        assertThat(columns.get(8).getValues()).containsExactly(8,2,6);
    }

    /**
     * 010  020  300
     * 004  005  060
     * 070  000  008
     *
     * 006  900  070
     * 000  100  002
     * 030  048  000
     *
     * 500  006  040
     * 000  800  106
     * 008  000  000
     */
    @Test
    public void testSquares_create_common9x9Sudoku() {
        Model model = ModelBuilder.create("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        List<Square> squares = model.getSquares();
        assertThat(squares).hasSize(9);
        assertThat(squares.get(0).getValues()).containsExactly(1,4,7);
        assertThat(squares.get(1).getValues()).containsExactly(2,5);
        assertThat(squares.get(2).getValues()).containsExactly(3,6,8);
        assertThat(squares.get(3).getValues()).containsExactly(6,3);
        assertThat(squares.get(4).getValues()).containsExactly(9,1,4,8);
        assertThat(squares.get(5).getValues()).containsExactly(7,2);
        assertThat(squares.get(6).getValues()).containsExactly(5,8);
        assertThat(squares.get(7).getValues()).containsExactly(6,8);
        assertThat(squares.get(8).getValues()).containsExactly(4,1,6);
    }

    @Test
    public void testModelBuilder_nonSquaredShapedSudoku() {
        assertThatThrownBy(() -> ModelBuilder.create("123")).isInstanceOf(IllegalArgumentException.class);

    }

}