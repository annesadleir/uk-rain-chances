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
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
public class RainChancesControllerSupplier implements Supplier<RainChancesController> {

    public static final String INVOCATION_NAME = "UK Rain Chances";

    public static final String EXAMPLE = "For example, ask " + INVOCATION_NAME
            + " about Exeter tomorrow between two pm and six pm, or, ask "
            + INVOCATION_NAME + " about the next five hours in Tiverton";

    @Override
    public RainChancesController get() {
        RainQueryValidator rainQueryValidator = new RainQueryValidator(Clock.system(ZoneId.of("Europe/London")));
        LocationCodeAscertainer locationCodeAscertainer = new LocationCodeAscertainer(existingLocationCodes());
        MobileWebsiteDataScraper mobileWebsiteDataScraper = new MobileWebsiteDataScraper();
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

    private List<LocationCode> existingLocationCodes() {
        return Lists.newArrayList(new LocationCode("Exeter", "gcj2x8gt4"),
                new LocationCode("Tiverton", "gcj98d6em"),
                new LocationCode("Uffculme", "gcj9s7564"));
    }
}
