package uk.co.littlestickyleaves.data;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import uk.co.littlestickyleaves.control.RainChancesControllerSupplier;
import uk.co.littlestickyleaves.domain.RainChancesException;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static uk.co.littlestickyleaves.control.RainChancesControllerSupplier.EXAMPLE;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO fill in Javadoc

public class IntentToRainQueryConverter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    private IntentToRainQueryConverter() {
    }

    public static RainQuery convertIntentToRainQuery(Intent intent) throws RainChancesException {
        Slot locationSlot = intent.getSlot("location");
        if (locationSlot == null) {
            throw new RainChancesException("Insufficient location information.  Try " + EXAMPLE);
        }

        try {
            Integer nextHours = Integer.parseInt(intent.getSlot("hours").getValue());
            return new RainQuery(locationSlot.getValue(), nextHours, null, null);
        } catch (NullPointerException npe) {
            // npe means just move on
        }

        Slot dateSlot = intent.getSlot("date");
        Slot startSlot = intent.getSlot("start");
        Slot endSlot = intent.getSlot("end");

        if (dateSlot == null || startSlot == null || endSlot == null) {
            throw new RainChancesException("Insufficient date information.  Try " + EXAMPLE);
        }

        LocalDate localDate = LocalDate.parse(dateSlot.getValue());
        LocalTime startTime = LocalTime.parse(startSlot.getValue());
        LocalTime endTime = LocalTime.parse(endSlot.getValue());

        return new RainQuery(locationSlot.getValue(), null,
                LocalDateTime.of(localDate, startTime),
                LocalDateTime.of(localDate, endTime));
    }

}
