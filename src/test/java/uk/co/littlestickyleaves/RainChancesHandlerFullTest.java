package uk.co.littlestickyleaves;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.Charsets;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Ignore
// not really a test more a way of trying it out
public class RainChancesHandlerFullTest {

    private static final ZoneId UK = ZoneId.of("Europe/London");

    private RainChancesHandler testObject;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        testObject = new RainChancesHandler();
    }

    @Test
    public void handleRequest() throws JsonProcessingException {
        // arrange
        RainQuery rainQuery = new RainQuery(
                "Exeter", 5, null, null
        );
        String json = objectMapper.writeValueAsString(rainQuery);

        try (InputStream inputStream = new ByteArrayInputStream(json.getBytes(Charsets.UTF_8));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // act
            testObject.handleRequest(inputStream, outputStream, null);

            // assert
            System.out.println(outputStream.toString("UTF-8"));
            System.out.println(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void handleRequestWellington() throws JsonProcessingException {
        // arrange
        ZonedDateTime startTime = ZonedDateTime.of(2017, 6, 14, 8, 0, 0, 0, UK);
        ZonedDateTime endTime = ZonedDateTime.of(2017, 6, 14, 11, 0, 0, 0, UK);

        RainQuery rainQuery = new RainQuery(
                "Inverness", null, startTime, endTime);

        String json = objectMapper.writeValueAsString(rainQuery);

        try (InputStream inputStream = new ByteArrayInputStream(json.getBytes(Charsets.UTF_8));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // act
            testObject.handleRequest(inputStream, outputStream, null);

            // assert
            System.out.println(outputStream.toString("UTF-8"));
            System.out.println(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}