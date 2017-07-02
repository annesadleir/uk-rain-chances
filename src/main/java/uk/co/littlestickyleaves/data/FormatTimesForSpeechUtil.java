package uk.co.littlestickyleaves.data;

import org.joda.time.Hours;
import uk.co.littlestickyleaves.dates.EnglishOrdinalDateFormatter;
import uk.co.littlestickyleaves.domain.PercentageAtTime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Utility to format time-related values for speech
 * -- formats the summary of what times are covered
 * -- formats groups of times as spans or separate times
 */

public class FormatTimesForSpeechUtil {

    private static final String SHORT_DATE_PATTERN = "EEEE 'the' ddd";
    private static final String LONG_DATE_PATTERN = SHORT_DATE_PATTERN + " 'of' MMMM";
    private static final String TIME_PATTERN = "h a";

    private static final EnglishOrdinalDateFormatter SHORT_DATE_FORMATTER =
            EnglishOrdinalDateFormatter.ofPattern(SHORT_DATE_PATTERN);
    private static final EnglishOrdinalDateFormatter LONG_DATE_FORMATTER =
            EnglishOrdinalDateFormatter.ofPattern(LONG_DATE_PATTERN);
    private static final EnglishOrdinalDateFormatter TIME_FORMATTER =
            EnglishOrdinalDateFormatter.ofPattern(TIME_PATTERN);

    public static String summarise(TreeSet<LocalDateTime> localDateTimes) {
//        if (localDateTimes.size() == 1) {
//            return TIME_FORMATTER.format(localDateTimes.first());
//        }
//
//        if (localDateTimes.size() == 2) {
//            return TIME_FORMATTER.format(localDateTimes.first()) + " and " + TIME_FORMATTER.format(localDateTimes.last());
//        }

        List<String> times = splitIntoConsecutives(localDateTimes).stream()
                .map(FormatTimesForSpeechUtil::formatSetOfTimes)
                .collect(Collectors.toList());

        int listSize = times.size();
        if (listSize > 1) {
            String last = times.get(listSize - 1);
            times.remove(listSize - 1);
            times.add("and " + last);
        }
        String delimiter = listSize == 2 ? " " : ", ";
        return times.stream().collect(Collectors.joining(delimiter));
    }

    private static String formatSetOfTimes(TreeSet<LocalDateTime> set) {
        if (set.size() == 1) {
            return "at " + TIME_FORMATTER.format(set.first());
        } else {
            return "from " + TIME_FORMATTER.format(set.first()) + " to " + TIME_FORMATTER.format(set.last());
        }
    }

    private static List<TreeSet<LocalDateTime>> splitIntoConsecutives(TreeSet<LocalDateTime> localDateTimes) {
        List<TreeSet<LocalDateTime>> split = new ArrayList<>();
        TreeSet<LocalDateTime> currentSet = new TreeSet<>();
        split.add(currentSet);
        LocalDateTime previous = null;

        for (LocalDateTime localDateTime : localDateTimes) {
            if (previous != null && Duration.between(previous, localDateTime).getSeconds() > 3600) {
                currentSet = new TreeSet<>();
                split.add(currentSet);
            }
            currentSet.add(localDateTime);
            previous = localDateTime;
        }
        return split;
    }

    public static String timeSpanAsSpeech(TreeSet<LocalDateTime> times) {
        return dateString(times)
                + " from " + TIME_FORMATTER.format(times.first())
                + " to " + TIME_FORMATTER.format(times.last()) + ": ";
    }

    private static String dateString(TreeSet<LocalDateTime> times) {
        TreeSet<LocalDate> datesCovered = times.stream()
                .map(LocalDateTime::toLocalDate)
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
