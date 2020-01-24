package uk.co.littlestickyleaves.data;

import org.junit.Before;
import org.junit.Test;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.time.*;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO fill in Javadoc

public class RainQueryValidatorTest {

    private RainQueryValidator testObject;

    private Clock clock = Clock.fixed(Instant.ofEpochMilli(1497344400000L), ZoneId.of("Europe/London"));

    private ZonedDateTime zonedDateTime = ZonedDateTime.now(clock);

    @Before
    public void setUp() throws Exception {
        testObject = new RainQueryValidator(clock);
    }

    @Test
    public void valid() throws Exception {
        // arrange
        RainQuery rainQuery = new RainQuery("location", null,
                zonedDateTime.plusHours(3), zonedDateTime.plusHours(6));

        // act
        testObject.validate(rainQuery);
    }


    @Test
    public void validNextHours() throws Exception {
        // arrange
        RainQuery rainQuery = new RainQuery("location", 8, null, null);

        // act
        testObject.validate(rainQuery);
    }


}