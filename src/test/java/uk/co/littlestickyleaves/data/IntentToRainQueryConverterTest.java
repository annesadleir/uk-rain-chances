package uk.co.littlestickyleaves.data;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.google.common.collect.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.littlestickyleaves.domain.RainChancesException;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class IntentToRainQueryConverterTest {

    private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(2017, Month.JULY, 2, 9, 0);
    private static final LocalDate TEST_DATE = TEST_DATE_TIME.toLocalDate();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void convertIntentWithHoursValue() throws Exception {
        // arrange
        Map<String, Slot> slotMap = new HashMap<>();
        slotMap.put("location", Slot.builder().withName("location").withValue("Exeter").build());
        slotMap.put("hours", Slot.builder().withName("hours").withValue("5").build());
        Intent intent = Intent.builder()
                .withName("not-important")
                .withSlots(slotMap)
                .build();

        // act
        RainQuery result = IntentToRainQueryConverter.convertIntentToRainQuery(intent);

        // assert
        assertEquals("Exeter", result.getLocation());
        assertEquals(new Integer(5), result.getNextHours());
        assertNull(result.getStart());
        assertNull(result.getEnd());
    }

    @Test
    public void convertIntentWithDateStartAndEndValues() throws Exception {
        // arrange
        String date = DateTimeFormatter.ISO_DATE.format(TEST_DATE);
        List<Slot> slots = Lists.newArrayList(
                Slot.builder().withName("location").withValue("Exeter").build(),
                Slot.builder().withName("date").withValue(date).build(),
                Slot.builder().withName("start").withValue("6").build(),
                Slot.builder().withName("end").withValue("11").build());
        Map<String, Slot> slotMap = slots.stream().collect(Collectors.toMap(Slot::getName, x -> x));
        Intent intent = Intent.builder()
                .withName("not-important")
                .withSlots(slotMap)
                .build();

        // act
        RainQuery result = IntentToRainQueryConverter.convertIntentToRainQuery(intent);

        // assert
        assertEquals("Exeter", result.getLocation());
        assertNull(result.getNextHours());
        assertEquals(TEST_DATE, result.getStart().toLocalDate());
        assertEquals(LocalTime.of(6, 0, 0), result.getStart().toLocalTime());
        assertEquals(TEST_DATE, result.getEnd().toLocalDate());
        assertEquals(LocalTime.of(11, 0, 0), result.getEnd().toLocalTime());
    }

    @Test
    public void convertIntentWithDateAndStartAndEndAsTimeValues() throws Exception {
        // arrange
        String date = DateTimeFormatter.ISO_DATE.format(TEST_DATE);
        List<Slot> slots = Lists.newArrayList(
                Slot.builder().withName("location").withValue("Exeter").build(),
                Slot.builder().withName("date").withValue(date).build(),
                Slot.builder().withName("start").withValue("08:00").build(),
                Slot.builder().withName("end").withValue("10:00").build());
        Map<String, Slot> slotMap = slots.stream().collect(Collectors.toMap(Slot::getName, x -> x));
        Intent intent = Intent.builder()
                .withName("not-important")
                .withSlots(slotMap)
                .build();

        // act
        RainQuery result = IntentToRainQueryConverter.convertIntentToRainQuery(intent);

        // assert
        assertEquals("Exeter", result.getLocation());
        assertNull(result.getNextHours());
        assertEquals(TEST_DATE, result.getStart().toLocalDate());
        assertEquals(LocalTime.of(8, 0, 0), result.getStart().toLocalTime());
        assertEquals(TEST_DATE, result.getEnd().toLocalDate());
        assertEquals(LocalTime.of(10, 0, 0), result.getEnd().toLocalTime());
    }

    @Test
    public void complainIfNoLocation() throws Exception {
        // arrange
        String date = DateTimeFormatter.ISO_DATE.format(TEST_DATE);
        List<Slot> slots = Lists.newArrayList(
                Slot.builder().withName("date").withValue(date).build(),
                Slot.builder().withName("start").withValue("6").build(),
                Slot.builder().withName("end").withValue("11").build());
        Map<String, Slot> slotMap = slots.stream().collect(Collectors.toMap(Slot::getName, x -> x));
        Intent intent = Intent.builder()
                .withName("not-important")
                .withSlots(slotMap)
                .build();
        expectedException.expect(RainChancesException.class);

        // act
        IntentToRainQueryConverter.convertIntentToRainQuery(intent);
    }
}