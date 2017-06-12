package uk.co.littlestickyleaves;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.littlestickyleaves.domain.EnglishOutput;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class RainChancesHandlerFullTest {

    private RainChancesHandler testObject;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        testObject = new RainChancesHandler();
    }

    @Test
    void handleRequest() throws JsonProcessingException {
        // arrange
        RainQuery rainQuery = new RainQuery(
                "Exeter", 5, null, null
        );

        // act
        EnglishOutput result = testObject.handleRequest(rainQuery, null);

        // assert
        System.out.println(result.getText());
        System.out.println(objectMapper.writeValueAsString(rainQuery));

    }

    @Test
    void handleRequestWellington() throws JsonProcessingException {
        // arrange
        RainQuery rainQuery = new RainQuery(
                "Wellington", null,
                LocalDateTime.of(2017, Month.JUNE, 13, 8, 0),
                LocalDateTime.of(2017, Month.JUNE, 13, 11, 0)
        );

        // act
        EnglishOutput result = testObject.handleRequest(rainQuery, null);

        // assert
        System.out.println(result.getText());
        System.out.println(objectMapper.writeValueAsString(rainQuery));
    }

}