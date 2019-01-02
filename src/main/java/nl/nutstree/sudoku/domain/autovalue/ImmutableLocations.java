package nl.nutstree.sudoku.domain.autovalue;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.HashSet;
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

    void validate() {

    }


    public static ImmutableLocations.Builder builder() {
        return new AutoValue_ImmutableLocations.Builder()
                .boardType(Type.SQUARE_9X9);
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder boardType(Type type);

        public abstract Builder allLocations(Collection<Location> locations);

        abstract ImmutableLocations autoBuild();  // not public

        public Locations build() {
            ImmutableLocations locations = autoBuild();
            locations.validate();

            return locations;
        }
    }
}
