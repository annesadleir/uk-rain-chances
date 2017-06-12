package uk.co.littlestickyleaves.domain;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO Javadoc
public class PercentageAtTime implements Comparable<PercentageAtTime> {

    private final LocalDateTime localDateTime;

    private final Percentages percentages;

    public PercentageAtTime(LocalDateTime localDateTime, Percentages percentages) {
        this.localDateTime = localDateTime;
        this.percentages = percentages;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Percentages getPercentages() {
        return percentages;
    }

    @Override
    public int compareTo(PercentageAtTime o) {
        return Comparator.comparing(PercentageAtTime::getLocalDateTime).compare(this, o);
    }
}
