package uk.co.littlestickyleaves.control;

import uk.co.littlestickyleaves.data.*;
import uk.co.littlestickyleaves.domain.*;
import uk.co.littlestickyleaves.helper.LongLatFinder;

import java.util.TreeSet;

/**
 * Controller handles the logic for the whole application
 * -- turns an Exception into a speech output
 */
public class RainChancesControllerTwo {

    private RainQueryValidator rainQueryValidator;

    private LongLatFinder longLatFinder;

    private DataHubCaller dataHubCaller;

    private TimedDataSelector timedDataSelector;

    private RainChancesTextCreator rainChancesTextCreator;

    public RainChancesControllerTwo(RainQueryValidator rainQueryValidator,
                                    LongLatFinder longLatFinder,
                                    DataHubCaller dataHubCaller,
                                    TimedDataSelector timedDataSelector,
                                    RainChancesTextCreator rainChancesTextCreator) {
        this.rainQueryValidator = rainQueryValidator;
        this.longLatFinder = longLatFinder;
        this.dataHubCaller = dataHubCaller;
        this.timedDataSelector = timedDataSelector;
        this.rainChancesTextCreator = rainChancesTextCreator;
    }

    public EnglishOutput go(RainQuery rainQuery) {

        try {
            rainQueryValidator.validate(rainQuery);

            // get the location code
            LongLat location = longLatFinder.locate();

            // get the data for that location
            TreeSet<PercentageAtTime> fullData = dataHubCaller.oneHourlyDataFor(location);

            // select the right time data
            TreeSet<PercentageAtTime> selectedData = timedDataSelector.apply(rainQuery, fullData);

            // convert it into text
            String output = rainChancesTextCreator.createText(rainQuery, selectedData);

            return new EnglishOutput(output);

        } catch (RainChancesException exception) {
            return new EnglishOutput(exception.getMessage());
        }
    }

}
