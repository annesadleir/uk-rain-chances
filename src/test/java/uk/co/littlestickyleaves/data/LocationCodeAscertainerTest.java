package uk.co.littlestickyleaves.data;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.littlestickyleaves.domain.LocationCode;

import java.util.List;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
class LocationCodeAscertainerTest {

    private LocationCodeAscertainer testObject;

    @BeforeEach
    void setUp() {
        List<LocationCode> locationCodes = Lists.newArrayList();
        testObject = new LocationCodeAscertainer(locationCodes);
    }

    @Test
    void tryOutFetchingData() {
        // arrange
        String place = "Wellington";

        // act
        LocationCode result = testObject.find(place);


        // assert
        System.out.println(result);
    }
}