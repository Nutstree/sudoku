package nl.nutstree.sudoku.domain.autovalue;

import java.util.Collection;
import java.util.Optional;

public interface Cell {
    Optional<Integer> getValue();

    Collection<Integer> getPossibilities();
}
