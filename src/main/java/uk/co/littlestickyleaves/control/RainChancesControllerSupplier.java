package uk.co.littlestickyleaves.control;

import com.google.common.collect.Lists;
import uk.co.littlestickyleaves.control.RainChancesController;
import uk.co.littlestickyleaves.data.*;
import uk.co.littlestickyleaves.domain.LocationCode;

import java.time.Clock;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Supplier;

/**
 * Class which deals with all instantiation of required objects
 * -- also has some constants for the application
 * -- idea is that it provides the application context
 */
public class RainChancesControllerSupplier implements Supplier<RainChancesController> {

    public static final String INVOCATION_NAME = "Rebecca's Rain Predictor";
    public static final ZoneId EUROPE_LONDON = ZoneId.of("Europe/London");
    public static final String EXAMPLE = "For example, ask " + INVOCATION_NAME
            + " about Exeter tomorrow between two pm and six pm, or, ask "
            + INVOCATION_NAME + " about the next five hours in Tiverton";

    private static final String LOCATION_SEARCH_URI = "http://www.metoffice.gov.uk/mobile/location-search/sitename";
    private static final String DATA_URI = "http://www.metoffice.gov.uk/mobile/forecast/";

    @Override
    public RainChancesController get() {
        RainQueryValidator rainQueryValidator = new RainQueryValidator(Clock.system(ZoneId.of("Europe/London")));

        LocationCodeAscertainer locationCodeAscertainer = new LocationCodeAscertainer(
                LOCATION_SEARCH_URI, existingLocationCodes());

        MobileWebsiteDataScraper mobileWebsiteDataScraper = new MobileWebsiteDataScraper(DATA_URI);

        TimedDataSelector timedDataSelector = new TimedDataSelector();

        RainChancesTextCreator rainChancesTextCreator = new RainChancesTextCreator();

        return new RainChancesController(
                rainQueryValidator,
                locationCodeAscertainer,
                mobileWebsiteDataScraper,
                timedDataSelector,
                rainChancesTextCreator
        );
    }

    // could be intelligently expanded
    private List<LocationCode> existingLocationCodes() {
        return Lists.newArrayList(new LocationCode("Exeter", "gcj2x8gt4"),
                new LocationCode("Tiverton", "gcj98d6em"),
                new LocationCode("Uffculme", "gcj9s7564"));
    }
}
