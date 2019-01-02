package nl.nutstree.sudoku.domain.autovalue;

import nl.nutstree.sudoku.domain.Location;
import nl.nutstree.sudoku.domain.Locations;

import java.util.Collection;
import java.util.Optional;

public interface Board {
    int getSize();

    Locations getLocations();

    Optional<Integer> getValue(Location location);

    Collection<Integer> getPossibilities(Location location);

    SquareBoard setValue(int value, Location location);

}
