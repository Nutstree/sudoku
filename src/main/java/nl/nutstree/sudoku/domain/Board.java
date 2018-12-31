package nl.nutstree.sudoku.domain;

import java.util.Collection;
import java.util.Optional;

public interface Board {
    int getSize();

    Optional<Integer> getValue(Location location);

    Collection<Integer> getPossibilities(Location location);

    void setValue(int value, Location location);

    Locations getLocations();

}
