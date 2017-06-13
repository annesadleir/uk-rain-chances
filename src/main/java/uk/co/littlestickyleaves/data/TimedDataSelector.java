package uk.co.littlestickyleaves.data;

import uk.co.littlestickyleaves.domain.PercentageAtTime;
import uk.co.littlestickyleaves.domain.RainChancesException;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO Javadoc
public class TimedDataSelector {
    public TreeSet<PercentageAtTime> select(RainQuery rainQuery, TreeSet<PercentageAtTime> fullData) {

        Integer nextHours = rainQuery.getNextHours();
        if (nextHours != null) {
            if (nextHours > 24 ) {
                throw new RainChancesException("Cannot answer for a period of more than 24 hours");
            }
            return fullData.stream()
                    .limit(rainQuery.getNextHours() + 1)
                    .collect(Collectors.toCollection(TreeSet::new));
        }

        LocalDateTime start = rainQuery.getStart();
        LocalDateTime end = rainQuery.getEnd();

        return fullData.stream()
                .filter(percentageAtTime -> !percentageAtTime.getLocalDateTime().isBefore(start))
                .filter(percentageAtTime -> !percentageAtTime.getLocalDateTime().isAfter(end))
                .collect(Collectors.toCollection(TreeSet::new));
    }
}