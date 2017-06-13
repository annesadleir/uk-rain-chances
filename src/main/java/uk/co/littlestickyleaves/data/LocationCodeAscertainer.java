package uk.co.littlestickyleaves.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import uk.co.littlestickyleaves.domain.LocationCode;
import uk.co.littlestickyleaves.domain.RainChancesException;

import java.io.IOException;
import java.util.List;

/**
 * Given a location name, finds a location code for the Met Office mobile website data
 * -- starts with some hard-coded values
 * -- otherwise looks it up, but will fail if it can't find an exact match, to prevent 'Budleigh' returning 'Butleigh'
 */
public class LocationCodeAscertainer {

    private static final String SEARCH_LOCATION_KEY = "searchLocation";

    private final String searchUrl;
    private final List<LocationCode> locationCodes;

    public LocationCodeAscertainer(String searchUrl, List<LocationCode> locationCodes) {
        this.searchUrl = searchUrl;
        this.locationCodes = locationCodes;
    }

    public LocationCode find(String location) {
        return locationCodes
                .stream()
                .filter(locationCode -> locationCode.getLocationName().equals(location))
                .findAny()
                .orElseGet(() -> queryForLocation(location));
    }

    private LocationCode queryForLocation(String location) {
        String uriWithQuery = searchUrl + "?" + SEARCH_LOCATION_KEY + "=" + location;
        Document fetchedDocument = fetchData(uriWithQuery);
        LocationCode result = fetchLocationCodeOfFirstResult(fetchedDocument);
        if (!result.getLocationName().equals(location)) {
            throw new RainChancesException("Sorry, I could not find data for location " + location
            + " in the Met Office mobile website");
        }
        return result;
    }

    private LocationCode fetchLocationCodeOfFirstResult(Document fetchedDocument) {
        Element searchResults = fetchedDocument.getElementsByAttributeValue("class", "search-results")
                .first();
        Element firstResult = searchResults.getElementsByTag("a").first();
        String link = firstResult.attr("href");
        String[] parts = link.split("/");
        String placeName = firstResult.text();
        return new LocationCode(placeName, parts[parts.length - 1]);
    }

    private Document fetchData(String uri) {
        try {
            return Jsoup.connect(uri).get();
        } catch (IOException e) {
            throw new RainChancesException("Unable to get location data from Met Office website");
        }
    }
}
