package uk.co.littlestickyleaves.helper;

import uk.co.littlestickyleaves.domain.LongLat;

// Returns only Exeter
public class LongLatFinder {

    private static final LongLat EXETER = new LongLat(-3.5339, 50.7184);

    public LongLat locate() {
        return EXETER;
    }
}
