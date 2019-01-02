package nl.nutstree.sudoku.domain.immutables;

import nl.nutstree.sudoku.domain.Location;
import nl.nutstree.sudoku.domain.Locations;
import nl.nutstree.sudoku.domain.Type;
import org.apache.commons.lang3.Validate;
import org.immutables.value.Value;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Value.Immutable
abstract class AbstractLocations implements Locations {
    static final String INVALID_LOCATION = "Location is made for a different board: %s, location: %s";

    @Value.Default
    public Type getBoardType() {
        return Type.SQUARE_9X9;
    }

    public abstract Collection<Location> getAllLocations();

    public Collection<Location> getLocationsInSameRow(Location referenceLocation) {
        validateLocation(referenceLocation);

        int row = referenceLocation.getRow();
        return getAllLocations().stream()
                .filter(location -> location.getRow() == row)
                .collect(Collectors.toSet());
    }

    public Collection<Location> getLocationsInSameColumn(Location referenceLocation) {
        validateLocation(referenceLocation);

        int column = referenceLocation.getColumn();
        return getAllLocations().stream()
                .filter(location -> location.getColumn() == column)
                .collect(Collectors.toSet());
    }

    public Collection<Location> getLocationsInSameQuadrant(Location referenceLocation) {
        validateLocation(referenceLocation);

        int quadrant = referenceLocation.getQuadrant();
        return getAllLocations().stream()
                .filter(location -> location.getQuadrant() == quadrant)
                .collect(Collectors.toSet());
    }

    public Collection<Location> getRelatedLocations(Location referenceLocation) {
        validateLocation(referenceLocation);

        Collection<Location> relatedLocations = new HashSet<>();
        relatedLocations.addAll(getLocationsInSameRow(referenceLocation));
        relatedLocations.addAll(getLocationsInSameColumn(referenceLocation));
        relatedLocations.addAll(getLocationsInSameQuadrant(referenceLocation));

        return relatedLocations;
    }

    void validateLocation(Location location) {
        Type typeOfLocation = ((ImmutableLocation) location).getBoardType();
        Validate.isTrue(typeOfLocation == getBoardType(), INVALID_LOCATION, getBoardType(), location);
    }

}
