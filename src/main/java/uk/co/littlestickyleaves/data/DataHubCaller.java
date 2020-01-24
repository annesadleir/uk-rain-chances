package uk.co.littlestickyleaves.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.geojson.FeatureCollection;
import uk.co.littlestickyleaves.domain.LongLat;
import uk.co.littlestickyleaves.domain.Percentage;
import uk.co.littlestickyleaves.domain.PercentageAtTime;
import uk.co.littlestickyleaves.domain.RainChancesException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DataHubCaller {

    private static final String CLIENT_ID = ""; // should be injected
    private static final String CLIENT_SECRET = ""; // should be injected

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public DataHubCaller(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    public TreeSet<PercentageAtTime> oneHourlyDataFor(LongLat location) {
        FeatureCollection featureCollection = fetchData(location);
        return extractRainChanges(featureCollection);
    }

    private FeatureCollection fetchData(LongLat location) {
        Request request = new Request.Builder()
                .url("https://api-metoffice.apiconnect.ibmcloud.com/metoffice/production/v0/forecasts/point/hourly" +
                        "?excludeParameterMetadata=true&includeLocationName=true" +
                        "&latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude())
                .get()
                .addHeader("x-ibm-client-id", CLIENT_ID)
                .addHeader("x-ibm-client-secret", CLIENT_SECRET)
                .addHeader("accept", "application/json")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            // TODO check for success first
            return objectMapper.readValue(response.body().string(), FeatureCollection.class);
        } catch (IOException e) {
            // TODO log exception
            throw new RainChancesException("I'm sorry, I cannot read the Met Office weather data right now");
        }
    }

    private TreeSet<PercentageAtTime> extractRainChanges(FeatureCollection featureCollection) {
        Object timeSeries = featureCollection.getFeatures().get(0).getProperties().get("timeSeries");
        List<Map<String, Object>> timeSeriesProperties = (List<Map<String, Object>>) timeSeries;

        return timeSeriesProperties.stream()
                .map(properties -> new PercentageAtTime(timeFrom(properties.get("time")),
                        Percentage.fromInput((int) properties.get("probOfPrecipitation"))))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private static ZonedDateTime timeFrom(Object time) {
        String timeString = (String) time;
        return ZonedDateTime.parse(timeString);
    }
}
