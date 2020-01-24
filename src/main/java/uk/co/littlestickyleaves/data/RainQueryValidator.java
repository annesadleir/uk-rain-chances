package uk.co.littlestickyleaves.data;

import uk.co.littlestickyleaves.domain.RainChancesException;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static uk.co.littlestickyleaves.control.RainChancesControllerSupplier.EXAMPLE;

/**
 * Makes sure a Rain Query has enough information
 * -- some of this may be a bit redundant, a remnant of fast piecemeal development
 */
public class RainQueryValidator {

    private Clock clock;

    public RainQueryValidator(Clock clock) {
        this.clock = clock;
    }

    public void validate(RainQuery rainQuery) {
        if (rainQuery == null) {
            exceptionIncludingExample("Invalid query");
        }

        if (rainQuery.getNextHours() != null) {
            validateNextHours(rainQuery);
        } else {
            validateStartAndEnd(rainQuery);
        }

    }

    private void validateStartAndEnd(RainQuery rainQuery) {
        ZonedDateTime start = rainQuery.getStart();
        ZonedDateTime end = rainQuery.getEnd();

        if (start == null || end == null) {
            exceptionIncludingExample("Invalid query");
        }

        if (start.isAfter(end)) {
            rainQuery.setEnd(end.plusDays(1));
        }

        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();
        LocalDate today = LocalDate.now(clock);
        LocalDate tomorrow = today.plusDays(1);


        if ((!startDate.isEqual(today) && !startDate.isEqual(tomorrow))
                || (!endDate.isEqual(today) && !endDate.isEqual(tomorrow))) {
            exceptionIncludingExample("Data only available for today and tomorrow");
        }
    }

    private void validateNextHours(RainQuery rainQuery) {
        if (rainQuery.getNextHours() < 0 || rainQuery.getNextHours() > 24) {
            exceptionIncludingExample("I don't do data for a period of " + rainQuery.getNextHours()
                    + " hours");
        }
    }

    private void exceptionIncludingExample(String prefix) {
        throw new RainChancesException(prefix + ". Try " + EXAMPLE);
    }
}
