package uk.co.littlestickyleaves.domain;

/**
 * POJO to hold a longitude, latitude pair.
 * Could easily get one from some library but this keeps the imports smaller
 */
public class LongLat {

    private double longitude;

    private double latitude;

    public LongLat(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "LongLat{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
