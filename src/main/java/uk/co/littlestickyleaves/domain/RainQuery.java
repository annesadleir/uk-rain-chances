package uk.co.littlestickyleaves.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

/**
 * Contains all necessary information about a query
 */
public class RainQuery {

    private String location;

    private Integer nextHours;

    @JsonSerialize(using = CustomLocalDateTimeSerialization.Serializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeSerialization.Deserializer.class)
    private LocalDateTime start;

    @JsonSerialize(using = CustomLocalDateTimeSerialization.Serializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeSerialization.Deserializer.class)
    private LocalDateTime end;

    public RainQuery() {
    }

    public RainQuery(String location, Integer nextHours, LocalDateTime start, LocalDateTime end) {
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

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public Integer getNextHours() {
        return nextHours;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

}
