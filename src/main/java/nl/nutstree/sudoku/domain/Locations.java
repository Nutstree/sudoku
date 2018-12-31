package nl.nutstree.sudoku.domain;

import java.util.Collection;

public interface Locations {

    Collection<Location> getAllLocations();

    Collection<Location> getLocationsInSameRow(Location referenceLocation);

    Collection<Location> getLocationsInSameColumn(Location referenceLocation);

    Collection<Location> getLocationsInSameQuadrant(Location referenceLocation);

    Collection<Location> getRelatedLocations(Location referenceLocation);
}
