package uk.co.littlestickyleaves.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import uk.co.littlestickyleaves.domain.LocationCode;
import uk.co.littlestickyleaves.domain.Percentages;
import uk.co.littlestickyleaves.domain.PercentageAtTime;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.*;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO Javadoc
public class MobileWebsiteDataScraper {

    private static final ZoneId EUROPE_LONDON = ZoneId.of("Europe/London");
    private static final String URI = "http://www.metoffice.gov.uk/mobile/forecast/gcj2x8gt4";

    public TreeSet<PercentageAtTime> dataFromDocument(LocationCode locationCode) {
        return fetchData(URI + locationCode.getLocationCode())
                .getElementById("weatherHolder")
                .children()
                .stream()
                .limit(2)
                .flatMap(this::unpackElement)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Document fetchData(String uri) {
        try {
            return Jsoup.connect(uri).get();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
            // TODO better exception
        }
    }

    private Stream<PercentageAtTime> unpackElement(Element element) {
        LocalDate localDate = extractLocalDate(element);
        List<LocalTime> hours = extractListOfHours(element);
        List<Percentages> percentages = extractListOfPercentages(element);

        if (hours.size() != percentages.size()) {
            throw new IllegalArgumentException("bad data"); // TODO better exception
        }

        return IntStream.range(0, hours.size())
                .boxed()
                .map(i -> new PercentageAtTime(LocalDateTime.of(localDate, hours.get(i)), percentages.get(i)));

    }

    private List<Percentages> extractListOfPercentages(Element element) {
        return element.getElementsByAttributeValue("class", "weatherRain wxContent")
                .stream()
                .flatMap(wxContent -> wxContent.children().stream())
                .map(Element::text)
                .map(Percentages::fromInput)
                .collect(Collectors.toList());
    }

    private List<LocalTime> extractListOfHours(Element element) {
        return element.getElementsByAttributeValue("class", "weatherTime")
                .stream()
                .flatMap(times -> times.children().stream())
                .map(Element::text)
                .map(Integer::parseInt)
                .map(i -> i / 100)
                .map(hour -> LocalTime.of(hour, 0))
                .collect(Collectors.toList());
    }

    private static LocalDate extractLocalDate(Element element) {
        String dayModule = element.attributes().get("class");
        String regex = "dayModule weatherDay\\d fcTime(\\d*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dayModule);
        if (matcher.find()) {
            String seconds = matcher.group(1);
            Long secondsLong = Long.parseLong(seconds);
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(secondsLong), EUROPE_LONDON).toLocalDate();
        } else {
            throw new IllegalArgumentException("no match");
        }
    }
}
