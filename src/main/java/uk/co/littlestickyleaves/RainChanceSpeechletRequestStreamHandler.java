package uk.co.littlestickyleaves;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.google.common.collect.Sets;
import uk.co.littlestickyleaves.control.RainChancesSpeechlet;

import java.util.HashSet;
import java.util.Set;

/**
 * The handler for Alexa use
 * -- sets up a SpeechletRequestStreamHandler with a specific Speechlet implementation
 * -- also specifies the Alexa App id, which it gets from an environment variable
 */
public class RainChanceSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    public RainChanceSpeechletRequestStreamHandler() {
        super(new RainChancesSpeechlet(),
                Sets.newHashSet(System.getenv("APP_ID")));
    }
}
