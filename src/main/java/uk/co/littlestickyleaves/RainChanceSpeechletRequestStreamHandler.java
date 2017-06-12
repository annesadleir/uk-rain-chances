package uk.co.littlestickyleaves;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.google.common.collect.Sets;
import uk.co.littlestickyleaves.control.RainChancesSpeechlet;

import java.util.HashSet;
import java.util.Set;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO fill in Javadoc

public class RainChanceSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    public RainChanceSpeechletRequestStreamHandler() {
        super(new RainChancesSpeechlet(),
                Sets.newHashSet(System.getenv("APP_ID")));
    }
}
