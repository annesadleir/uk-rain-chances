package uk.co.littlestickyleaves.control;

import uk.co.littlestickyleaves.data.*;
import uk.co.littlestickyleaves.domain.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeSet;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
public class RainChancesController {

    private RainQueryValidator rainQueryValidator;

    private LocationCodeAscertainer locationCodeAscertainer;

    private MobileWebsiteDataScraper mobileWebsiteDataScraper;

    private TimedDataSelector timedDataSelector;

    private RainChancesTextCreator rainChancesTextCreator;

    public RainChancesController(RainQueryValidator rainQueryValidator,
                                 LocationCodeAscertainer locationCodeAscertainer,
                                 MobileWebsiteDataScraper mobileWebsiteDataScraper,
                                 TimedDataSelector timedDataSelector,
                                 RainChancesTextCreator rainChancesTextCreator) {
        this.rainQueryValidator = rainQueryValidator;
        this.locationCodeAscertainer = locationCodeAscertainer;
        this.mobileWebsiteDataScraper = mobileWebsiteDataScraper;
        this.timedDataSelector = timedDataSelector;
        this.rainChancesTextCreator = rainChancesTextCreator;
    }

    public EnglishOutput go(RainQuery rainQuery) {

        try {
            rainQueryValidator.validate(rainQuery);

            // get the location code
            LocationCode locationCode = locationCodeAscertainer.find(rainQuery.getLocation());

            // get the data for that location
            TreeSet<PercentageAtTime> fullData = mobileWebsiteDataScraper.dataFromDocument(locationCode);

            // select the right time data
            TreeSet<PercentageAtTime> selectedData = timedDataSelector.select(rainQuery, fullData);

            // convert it into text
            String output = rainChancesTextCreator.createText(rainQuery, selectedData);

            return new EnglishOutput(output);

        } catch (RainChancesException exception) {
            return new EnglishOutput(exception.getMessage());
        }
    }

}
