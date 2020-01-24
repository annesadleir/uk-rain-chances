package uk.co.littlestickyleaves.domain;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Comparator;

/**
 * Pairs a percentage with a time
 * -- sorts on time alone
 */
public class PercentageAtTime implements Comparable<PercentageAtTime> {

    private final ZonedDateTime zonedDateTime;

    private final Percentage percentage;

    public PercentageAtTime(ZonedDateTime zonedDateTime, Percentage percentage) {
        this.zonedDateTime = zonedDateTime;
        this.percentage = percentage;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public Percentage getPercentage() {
        return percentage;
    }

    @Override
    public int compareTo(PercentageAtTime o) {
        return Comparator.comparing(PercentageAtTime::getZonedDateTime).compare(this, o);
    }
}
