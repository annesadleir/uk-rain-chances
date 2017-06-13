package uk.co.littlestickyleaves;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.littlestickyleaves.domain.EnglishOutput;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.time.LocalDateTime;
import java.time.Month;

@Ignore
// not really a test more a way of trying it out
public class RainChancesHandlerFullTest {

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

        // act
        EnglishOutput result = testObject.handleRequest(rainQuery, null);

        // assert
        System.out.println(result.getText());
        System.out.println(objectMapper.writeValueAsString(rainQuery));

    }

    @Test
    public void handleRequestWellington() throws JsonProcessingException {
        // arrange
        RainQuery rainQuery = new RainQuery(
                "Inverness", null,
                LocalDateTime.of(2017, Month.JUNE, 14, 8, 0),
                LocalDateTime.of(2017, Month.JUNE, 14, 11, 0)
        );

        // act
        EnglishOutput result = testObject.handleRequest(rainQuery, null);

        // assert
        System.out.println(result.getText());
        System.out.println(objectMapper.writeValueAsString(rainQuery));
    }

}