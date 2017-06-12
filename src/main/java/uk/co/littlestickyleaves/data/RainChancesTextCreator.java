package uk.co.littlestickyleaves.data;

import uk.co.littlestickyleaves.dates.EnglishOrdinalDateFormatter;
import uk.co.littlestickyleaves.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO Javadoc
public class RainChancesTextCreator {

    public static final String SHORT_DATE_PATTERN = "EEEE 'the' ddd";
    private static final String LONG_DATE_PATTERN = SHORT_DATE_PATTERN + " 'of' MMMM";
    private static final String TIME_PATTERN = "h a";

    private static final EnglishOrdinalDateFormatter SHORT_DATE_FORMATTER =
            EnglishOrdinalDateFormatter.ofPattern(SHORT_DATE_PATTERN);
    private static final EnglishOrdinalDateFormatter LONG_DATE_FORMATTER =
            EnglishOrdinalDateFormatter.ofPattern(LONG_DATE_PATTERN);
    private static final EnglishOrdinalDateFormatter TIME_FORMATTER =
            EnglishOrdinalDateFormatter.ofPattern(TIME_PATTERN);

    public String createText(RainQuery rainQuery, TreeSet<PercentageAtTime> selectedData) {
        return introduction(rainQuery, selectedData)
                + summary(selectedData);
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

        return  " The highest chance of rain is " + keys.last().getOutput()
                + " at " + timesAsSpeech(groupedData.get(keys.last()))
                + " and the lowest chance is " + keys.first().getOutput()
                + " at " + timesAsSpeech(groupedData.get(keys.first()));
    }

    private String timesAsSpeech(TreeSet<LocalDateTime> localDateTimes) {
        List<String> times = localDateTimes.stream()
                .map(TIME_FORMATTER::format)
                .collect(Collectors.toList());
        int listSize = times.size();
        if (listSize > 1) {
            String last = times.get(listSize - 1);
            times.remove(listSize - 1);
            times.add("and " + last);
        }
        return times.stream().collect(Collectors.joining(", "));
    }

    private String summaryOfIdentical(Map<Percentages, TreeSet<LocalDateTime>> groupedData) {
        return " throughout this period the chance of rain is " + groupedData.keySet()
                .stream()
                .findAny()
                .map(Percentages::getOutput)
                .orElseThrow(() -> new RainChancesException("Terrible internal error"));
    }

    private String introduction(RainQuery rainQuery, TreeSet<PercentageAtTime> selectedData) {
        return "Here are the chances of rain in "
                + rainQuery.getLocation() + " on "
                 + dateString(selectedData)
                + " from " + TIME_FORMATTER.format(selectedData.first().getLocalDateTime())
                + " to " + TIME_FORMATTER.format(selectedData.last().getLocalDateTime()) + ": ";
    }

    private String dateString(TreeSet<PercentageAtTime> selectedData) {
        TreeSet<LocalDate> datesCovered = selectedData.stream()
                .map(percentageAtTime -> percentageAtTime.getLocalDateTime().toLocalDate())
                .collect(Collectors.toCollection(TreeSet::new));

        String prefix = "";
        if (datesCovered.size() > 1) {
            if (datesCovered.first().getMonth() != datesCovered.last().getMonth()) {
                prefix = LONG_DATE_FORMATTER.format(datesCovered.first()) + " and ";
            } else {
                prefix = SHORT_DATE_FORMATTER.format(datesCovered.first()) + " and ";
            }
        }

        return prefix + LONG_DATE_FORMATTER.format(datesCovered.last());

    }
}
