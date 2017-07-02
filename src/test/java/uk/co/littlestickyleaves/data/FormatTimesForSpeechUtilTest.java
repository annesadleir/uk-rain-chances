package uk.co.littlestickyleaves.data;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class FormatTimesForSpeechUtilTest {

    public static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(2017, Month.JULY, 15,
            8, 0, 0);

    @Test
    public void timeSpanAsSpeech() throws Exception {
        // arrange
        TreeSet<LocalDateTime> localDateTimes = Sets.newTreeSet();
        localDateTimes.add(TEST_DATE_TIME);
        localDateTimes.add(TEST_DATE_TIME.plusHours(4));
        String expectedResult = "Saturday the 15th of July from 8 AM to 12 PM: ";

        // act
        String result = FormatTimesForSpeechUtil.timeSpanAsSpeech(localDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseOneHour() throws Exception {
        // arrange
        TreeSet<LocalDateTime> localDateTimes = Sets.newTreeSet();
        localDateTimes.add(TEST_DATE_TIME);
        String expectedResult = "at 8 AM";

        // act
        String result = FormatTimesForSpeechUtil.summarise(localDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseTwoHourRange() throws Exception {
        // arrange
        TreeSet<LocalDateTime> localDateTimes = Sets.newTreeSet();
        localDateTimes.add(TEST_DATE_TIME);
        localDateTimes.add(TEST_DATE_TIME.plusHours(1));
        String expectedResult = "from 8 AM to 9 AM";

        // act
        String result = FormatTimesForSpeechUtil.summarise(localDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseThreeHourRange() throws Exception {
        // arrange
        TreeSet<LocalDateTime> localDateTimes = Sets.newTreeSet();
        localDateTimes.add(TEST_DATE_TIME);
        localDateTimes.add(TEST_DATE_TIME.plusHours(1));
        localDateTimes.add(TEST_DATE_TIME.plusHours(2));
        String expectedResult = "from 8 AM to 10 AM";

        // act
        String result = FormatTimesForSpeechUtil.summarise(localDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseRangeAndSingleHour() throws Exception {
        // arrange
        TreeSet<LocalDateTime> localDateTimes = Sets.newTreeSet();
        localDateTimes.add(TEST_DATE_TIME);
        localDateTimes.add(TEST_DATE_TIME.plusHours(1));
        localDateTimes.add(TEST_DATE_TIME.plusHours(2));
        localDateTimes.add(TEST_DATE_TIME.plusHours(4));
        String expectedResult = "from 8 AM to 10 AM and at 12 PM";

        // act
        String result = FormatTimesForSpeechUtil.summarise(localDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseSeveralSingleHours() throws Exception {
        // arrange
        TreeSet<LocalDateTime> localDateTimes = Sets.newTreeSet();
        localDateTimes.add(TEST_DATE_TIME);
        localDateTimes.add(TEST_DATE_TIME.plusHours(2));
        localDateTimes.add(TEST_DATE_TIME.plusHours(4));
        String expectedResult = "at 8 AM, at 10 AM, and at 12 PM";

        // act
        String result = FormatTimesForSpeechUtil.summarise(localDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void summariseMixOfRangesAndSingleHours() throws Exception {
        // arrange
        TreeSet<LocalDateTime> localDateTimes = Sets.newTreeSet();
        localDateTimes.add(TEST_DATE_TIME);
        localDateTimes.add(TEST_DATE_TIME.plusHours(1));
        localDateTimes.add(TEST_DATE_TIME.plusHours(2));
        localDateTimes.add(TEST_DATE_TIME.plusHours(4));
        localDateTimes.add(TEST_DATE_TIME.plusHours(6));
        localDateTimes.add(TEST_DATE_TIME.plusHours(8));
        localDateTimes.add(TEST_DATE_TIME.plusHours(9));
        String expectedResult = "from 8 AM to 10 AM, at 12 PM, at 2 PM, and from 4 PM to 5 PM";

        // act
        String result = FormatTimesForSpeechUtil.summarise(localDateTimes);

        // assert
        assertEquals(expectedResult, result);
    }

}