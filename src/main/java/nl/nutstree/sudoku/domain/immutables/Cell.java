package nl.nutstree.sudoku.domain.immutables;

import java.util.Optional;
import java.util.Set;

public interface Cell {
    Optional<Integer> getValue();

    Set<Integer> getPossibilities();
}
