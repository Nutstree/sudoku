package nl.nutstree.sudoku.domain;

import java.util.Collection;
import java.util.Optional;

public interface Board {
    int getSize();

    Optional<Integer> getValue(Location location);

    Collection<Integer> getPossibilities(Location location);

    void setValue(int value, Location location);

    Collection<Location> getAllLocations();

    Collection<Location> getLocationsInSameRow(Location referenceLocation);

    Collection<Location> getLocationsInSameColumn(Location referenceLocation);

    Collection<Location> getLocationsInSameQuadrant(Location referenceLocation);

    Collection<Location> getRelatedLocations(Location referenceLocation);
}
