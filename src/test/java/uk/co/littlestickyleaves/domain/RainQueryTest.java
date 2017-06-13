package uk.co.littlestickyleaves.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class RainQueryTest {

    @Test
    public void jsonRoundTrip() throws Exception {

        // arrange
        ObjectMapper objectMapper = new ObjectMapper();
        RainQuery rainQuery = new RainQuery("location",
                null, LocalDateTime.of(2017, Month.JUNE, 15, 10, 0, 0),
                LocalDateTime.of(2017, Month.JUNE, 15, 14, 0, 0));

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