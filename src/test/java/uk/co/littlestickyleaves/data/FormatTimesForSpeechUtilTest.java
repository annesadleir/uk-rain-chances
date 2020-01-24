package uk.co.littlestickyleaves.data;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class FormatTimesForSpeechUtilTest {

    private static final ZoneId UK = ZoneId.of("Europe/London");

    public static final ZonedDateTime TEST_DATE_TIME = ZonedDateTime.of(2017, 7, 15,
            8, 0, 0, 0, UK);

    @Test
    public void timeSpanAsSpeech() throws Exception {
        // arrange
        TreeSet<ZonedDateTime> zonedDateTimes = Sets.newTreeSet();
        zonedDateTimes.add(TEST_DATE_TIME);
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(4));
        String expectedResult = "Saturday the 15th of July from 8 am to 12 pm: ";

        // act
        String result = FormatTimesForSpeechUtil.timeSpanAsSpeech(zonedDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseOneHour() throws Exception {
        // arrange
        TreeSet<ZonedDateTime> zonedDateTimes = Sets.newTreeSet();
        zonedDateTimes.add(TEST_DATE_TIME);
        String expectedResult = "at 8 am";

        // act
        String result = FormatTimesForSpeechUtil.summarise(zonedDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseTwoHourRange() throws Exception {
        // arrange
        TreeSet<ZonedDateTime> zonedDateTimes = Sets.newTreeSet();
        zonedDateTimes.add(TEST_DATE_TIME);
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(1));
        String expectedResult = "from 8 am to 9 am";

        // act
        String result = FormatTimesForSpeechUtil.summarise(zonedDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseThreeHourRange() throws Exception {
        // arrange
        TreeSet<ZonedDateTime> zonedDateTimes = Sets.newTreeSet();
        zonedDateTimes.add(TEST_DATE_TIME);
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(1));
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(2));
        String expectedResult = "from 8 am to 10 am";

        // act
        String result = FormatTimesForSpeechUtil.summarise(zonedDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseRangeAndSingleHour() throws Exception {
        // arrange
        TreeSet<ZonedDateTime> zonedDateTimes = Sets.newTreeSet();
        zonedDateTimes.add(TEST_DATE_TIME);
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(1));
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(2));
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(4));
        String expectedResult = "from 8 am to 10 am and at 12 pm";

        // act
        String result = FormatTimesForSpeechUtil.summarise(zonedDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseSeveralSingleHours() throws Exception {
        // arrange
        TreeSet<ZonedDateTime> zonedDateTimes = Sets.newTreeSet();
        zonedDateTimes.add(TEST_DATE_TIME);
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(2));
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(4));
        String expectedResult = "at 8 am, at 10 am, and at 12 pm";

        // act
        String result = FormatTimesForSpeechUtil.summarise(zonedDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseMixOfRangesAndSingleHours() throws Exception {
        // arrange
        TreeSet<ZonedDateTime> zonedDateTimes = Sets.newTreeSet();
        zonedDateTimes.add(TEST_DATE_TIME);
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(1));
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(2));
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(4));
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(6));
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(8));
        zonedDateTimes.add(TEST_DATE_TIME.plusHours(9));
        String expectedResult = "from 8 am to 10 am, at 12 pm, at 2 pm, and from 4 pm to 5 pm";

        // act
        String result = FormatTimesForSpeechUtil.summarise(zonedDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

}