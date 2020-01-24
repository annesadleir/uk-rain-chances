package uk.co.littlestickyleaves.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Contains all necessary information about a query
 */
public class RainQuery {

    private String location;

    private Integer nextHours;

//    @JsonSerialize(using = CustomLocalDateTimeSerialization.Serializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeSerialization.Deserializer.class)
    private ZonedDateTime start;

//    @JsonSerialize(using = CustomLocalDateTimeSerialization.Serializer.class)
//    @JsonDeserialize(using = CustomLocalDateTimeSerialization.Deserializer.class)
    private ZonedDateTime end;

    public RainQuery() {
    }

    public RainQuery(String location, Integer nextHours, ZonedDateTime start, ZonedDateTime end) {
        this.location = location;
        this.nextHours = nextHours;
        this.start = start;
        this.end = end;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNextHours(Integer nextHours) {
        this.nextHours = nextHours;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public Integer getNextHours() {
        return nextHours;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

}
