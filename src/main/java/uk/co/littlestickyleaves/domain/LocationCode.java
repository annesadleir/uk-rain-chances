package uk.co.littlestickyleaves.domain;

/**
 * Just holds a Location name and Location's code on the Met Office mobile website
 */
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
