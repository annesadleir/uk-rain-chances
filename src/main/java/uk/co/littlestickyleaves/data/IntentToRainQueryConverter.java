package uk.co.littlestickyleaves.data;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import uk.co.littlestickyleaves.domain.RainChancesException;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static uk.co.littlestickyleaves.control.RainChancesControllerSupplier.EXAMPLE;

/**
 * Utility to convert an Intent to a RainQuery
 * -- future work could make sure this covers all the input options, which are varied
 * -- https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/built-in-intent-ref/slot-type-reference#date
 * -- https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/built-in-intent-ref/slot-type-reference#time
 */
public class IntentToRainQueryConverter {

    private IntentToRainQueryConverter() {
    }

    public static RainQuery convertIntentToRainQuery(Intent intent) throws RainChancesException {
        Slot locationSlot = intent.getSlot("location");
        if (locationSlot == null) {
            throw new RainChancesException("Insufficient location information.  Try " + EXAMPLE);
        }

        if (intent.getSlot("hours") != null && intent.getSlot("hours").getValue() != null) {
            Integer nextHours = Integer.parseInt(intent.getSlot("hours").getValue());
            return new RainQuery(locationSlot.getValue(), nextHours, null, null);
        }

        Slot dateSlot = intent.getSlot("date");
        Slot startSlot = intent.getSlot("start");
        Slot endSlot = intent.getSlot("end");

        if (dateSlot == null || startSlot == null || endSlot == null) {
            throw new RainChancesException("Insufficient date information.  Try " + EXAMPLE);
        }

        LocalDate localDate = LocalDate.parse(dateSlot.getValue());
        LocalTime startTime = parseAsTime(startSlot.getValue());
        LocalTime endTime = parseAsTime(endSlot.getValue());

        return new RainQuery(locationSlot.getValue(), null,
                LocalDateTime.of(localDate, startTime),
                LocalDateTime.of(localDate, endTime));
    }

    // time can come in as different formats
    // this could possibly be more defensive and thorough
    private static LocalTime parseAsTime(String value) {
        try {
            Integer hourOfDay = Integer.parseInt(value);
            return LocalTime.of(hourOfDay, 0);
        } catch (NumberFormatException exception) {
            return LocalTime.parse(value);
        }
    }

}
