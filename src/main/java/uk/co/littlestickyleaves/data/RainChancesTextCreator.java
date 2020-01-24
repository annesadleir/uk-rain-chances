package uk.co.littlestickyleaves.data;

import uk.co.littlestickyleaves.domain.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Converts rain chances data into text
 * -- uses my EnglishOrdinalDateFormatter code!
 * -- could be improved by, e.g. summarising ranges of times with the same probability
 */
public class RainChancesTextCreator {

    public String createText(RainQuery rainQuery, TreeSet<PercentageAtTime> selectedData) {
        return introduction(rainQuery, selectedData)
                + summary(selectedData);
    }

    private String introduction(RainQuery rainQuery, TreeSet<PercentageAtTime> selectedData) {
        TreeSet<ZonedDateTime> times = selectedData.stream()
                .map(PercentageAtTime::getZonedDateTime)
                .collect(Collectors.toCollection(TreeSet::new));
        return "Here are the chances of rain in "
                + rainQuery.getLocation() + " on "
                + FormatTimesForSpeechUtil.timeSpanAsSpeech(times);
    }

    private String summary(TreeSet<PercentageAtTime> selectedData) {
        Map<Percentage, TreeSet<ZonedDateTime>> groupedData = selectedData.stream()
                .collect(Collectors.groupingBy(PercentageAtTime::getPercentage,
                        Collectors.mapping(PercentageAtTime::getZonedDateTime, Collectors.toCollection(TreeSet::new))));

        if (groupedData.size() == 1) {
            return summaryOfIdentical(groupedData);
        } else {
            return summaryOfDiffering(groupedData);
        }
    }

    private String summaryOfDiffering(Map<Percentage, TreeSet<ZonedDateTime>> groupedData) {
        TreeSet<Percentage> keys = new TreeSet<>(groupedData.keySet());

        return  "The highest chance of rain is " + keys.last().getOutput()
                + " " + FormatTimesForSpeechUtil.summarise(groupedData.get(keys.last()))
                + ", and the lowest chance is " + keys.first().getOutput()
                + " " + FormatTimesForSpeechUtil.summarise(groupedData.get(keys.first()));
    }

    private String summaryOfIdentical(Map<Percentage, TreeSet<ZonedDateTime>> groupedData) {
        return " throughout this period the chance of rain is " + groupedData.keySet()
                .stream()
                .findAny()
                .map(Percentage::getOutput)
                .orElseThrow(() -> new RainChancesException("Terrible internal error"));
    }

}
