package uk.co.littlestickyleaves.domain;

/**
 * Just a single exception for anything that goes wrong
 * -- intended to carry a message which could be read aloud
 */
public class RainChancesException extends RuntimeException {
    public RainChancesException(String message) {
        super(message);
    }
}
