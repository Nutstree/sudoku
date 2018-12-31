package nl.nutstree.sudoku.domain;

import java.util.Optional;
import java.util.Set;

public interface Cell {
    Optional<Integer> getValue();

    Set<Integer> getPossibilities();
}
