package nl.nutstree.sudoku.domain;

import java.util.Collection;

public interface Board {
    int getSize();

    int getValue(Location location);

    Collection<Integer> getPossibilities(Location location);

    void setValue(int value, Location location);

    Collection<Location> getAllLocations();

    Collection<Location> getLocationsInRow(Location referenceLocation);

    Collection<Location> getLocationsInColumn(Location referenceLocation);

    Collection<Location> getLocationsInQuadrant(Location referenceLocation);

    Collection<Location> getRelatedLocations(Location referenceLocation);
}
