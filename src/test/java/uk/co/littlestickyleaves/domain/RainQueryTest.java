package uk.co.littlestickyleaves.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class RainQueryTest {

    private static final ZoneId UK = ZoneId.of("Europe/London");

    @Test
    public void jsonRoundTrip() throws Exception {

        // arrange
        ObjectMapper objectMapper = new ObjectMapper();
        ZonedDateTime startTime =  ZonedDateTime.of(2017, 6, 15, 10, 0, 0, 0, UK);
        ZonedDateTime endTime =  ZonedDateTime.of(2017, 6, 15, 14, 0, 0, 0, UK);
        RainQuery rainQuery = new RainQuery("location",null, startTime, endTime);

        // act
        String serialized = objectMapper.writeValueAsString(rainQuery);
        RainQuery deserialized = objectMapper.readValue(serialized, RainQuery.class);
        String reserialized = objectMapper.writeValueAsString(deserialized);

        // assert
        Assert.assertEquals(reserialized, serialized);
        System.out.println(serialized);
    }

    @Test
    public void jsonRoundTripWithNextHours() throws Exception {

        // arrange
        ObjectMapper objectMapper = new ObjectMapper();
        RainQuery rainQuery = new RainQuery("location",8, null, null);

        // act
        String serialized = objectMapper.writeValueAsString(rainQuery);
        RainQuery deserialized = objectMapper.readValue(serialized, RainQuery.class);
        String reserialized = objectMapper.writeValueAsString(deserialized);

        // assert
        Assert.assertEquals(reserialized, serialized);
    }
}