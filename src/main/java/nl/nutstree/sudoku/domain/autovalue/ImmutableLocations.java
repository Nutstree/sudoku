package nl.nutstree.sudoku.domain.autovalue;

import com.google.auto.value.AutoValue;
import nl.nutstree.sudoku.domain.Location;
import nl.nutstree.sudoku.domain.Locations;
import nl.nutstree.sudoku.domain.Type;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AutoValue
abstract class ImmutableLocations implements Locations {
    static final String INVALID_LOCATION = "Location is made for a different board: %s, location: %s";

    //    @Value.Default
    public abstract Type getBoardType();

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

    public void validateLocation(Location location) {
        Type typeOfLocation = ((ImmutableLocation) location).getBoardType();
        Validate.isTrue(typeOfLocation == getBoardType(), INVALID_LOCATION, getBoardType(), location);
    }

    public static ImmutableLocations of(Type boardType) {
        return new AutoValue_ImmutableLocations(boardType, createLocations(boardType));
    }

    private static Set<Location> createLocations(Type type) {
        Set<Location> locations = new HashSet<>();
        for (int y = 0; y < type.getSize(); y++) {
            for (int x = 0; x < type.getSize(); x++) {
                locations.add(ImmutableLocation.of(x, y));
            }
        }

        return locations;
    }
}
