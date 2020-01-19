package uk.co.littlestickyleaves.domain;

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