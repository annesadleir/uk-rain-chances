package uk.co.littlestickyleaves.domain;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO Javadoc
public class LocationCode {

    private String locationName;

    private String locationCode;

    public LocationCode(String locationName, String locationCode) {
        this.locationName = locationName;
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationCode() {
        return locationCode;
    }

    @Override
    public String toString() {
        return "LocationCode{" +
                "locationName='" + locationName + '\'' +
                ", locationCode='" + locationCode + '\'' +
                '}';
    }
}
