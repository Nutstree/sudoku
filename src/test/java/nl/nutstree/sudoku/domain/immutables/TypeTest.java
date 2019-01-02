package nl.nutstree.sudoku.domain.immutables;

import nl.nutstree.sudoku.domain.Type;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TypeTest {

    @Test
    public void invalidBoardSize_throwsException() {
        assertThatThrownBy(() -> Type.valueOf(5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void validBoardSize_4x4() {
        Type type = Type.valueOf(4);

        assertThat(type.getSize()).isEqualTo(4);
        assertThat(type.getValidValues()).containsExactlyInAnyOrder(1, 2, 3, 4);
    }

    @Test
    public void validBoardSize_9x9() {
        Type type = Type.valueOf(9);

        assertThat(type.getSize()).isEqualTo(9);
        assertThat(type.getValidValues()).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
}