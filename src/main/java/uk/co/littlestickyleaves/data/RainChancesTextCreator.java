package uk.co.littlestickyleaves.data;

import uk.co.littlestickyleaves.dates.EnglishOrdinalDateFormatter;
import uk.co.littlestickyleaves.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        TreeSet<LocalDateTime> times = selectedData.stream()
                .map(PercentageAtTime::getLocalDateTime)
                .collect(Collectors.toCollection(TreeSet::new));
        return "Here are the chances of rain in "
                + rainQuery.getLocation() + " on "
                + FormatTimesForSpeechUtil.timeSpanAsSpeech(times);
    }

    private String summary(TreeSet<PercentageAtTime> selectedData) {
        Map<Percentages, TreeSet<LocalDateTime>> groupedData = selectedData.stream()
                .collect(Collectors.groupingBy(PercentageAtTime::getPercentages,
                        Collectors.mapping(PercentageAtTime::getLocalDateTime, Collectors.toCollection(TreeSet::new))));

        if (groupedData.size() == 1) {
            return summaryOfIdentical(groupedData);
        } else {
            return summaryOfDiffering(groupedData);
        }
    }

    private String summaryOfDiffering(Map<Percentages, TreeSet<LocalDateTime>> groupedData) {
        TreeSet<Percentages> keys = new TreeSet<>(groupedData.keySet());

        return  "The highest chance of rain is " + keys.last().getOutput()
                + " at " + FormatTimesForSpeechUtil.summarise(groupedData.get(keys.last()))
                + ", and the lowest chance is " + keys.first().getOutput()
                + FormatTimesForSpeechUtil.summarise(groupedData.get(keys.first()));
    }

    private String summaryOfIdentical(Map<Percentages, TreeSet<LocalDateTime>> groupedData) {
        return " throughout this period the chance of rain is " + groupedData.keySet()
                .stream()
                .findAny()
                .map(Percentages::getOutput)
                .orElseThrow(() -> new RainChancesException("Terrible internal error"));
    }

}
