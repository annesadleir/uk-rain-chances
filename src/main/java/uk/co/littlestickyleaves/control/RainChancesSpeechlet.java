package uk.co.littlestickyleaves.control;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.littlestickyleaves.RainChancesHandler;
import uk.co.littlestickyleaves.data.IntentToRainQueryConverter;
import uk.co.littlestickyleaves.domain.EnglishOutput;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO Javadoc
public class RainChancesSpeechlet implements Speechlet {

    private static final Logger log = LoggerFactory.getLogger(RainChancesSpeechlet.class);

    private RainChancesHandler rainChancesHandler;

    private Supplier<RainChancesController> rainChancesControllerSupplier;

    @Override
    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", sessionStartedRequest.getRequestId(),
                session.getSessionId());
        rainChancesHandler = new RainChancesHandler();
        rainChancesControllerSupplier = new RainChancesControllerSupplier();
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", launchRequest.getRequestId(),
                session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", intentRequest.getRequestId(),
                session.getSessionId());

        Intent intent = intentRequest.getIntent();
        String intentName = intent.getName();

        if (intentName.equals("RainChancesQuery")) {
            RainQuery rainQuery = IntentToRainQueryConverter.convertIntentToRainQuery(intent);
            EnglishOutput output = rainChancesControllerSupplier.get().go(rainQuery);
            return makeTellResponseWithCard(output.getText());
        } else if (intentName.equals("AMAZON.HelpIntent")) {
            return getWelcomeResponse();
        } else {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("Goodbye");
            return SpeechletResponse.newTellResponse(outputSpeech);
        }
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", sessionEndedRequest.getRequestId(),
                session.getSessionId());
    }

    private SpeechletResponse getWelcomeResponse() {
        String text = "You can ask " + RainChancesControllerSupplier.INVOCATION_NAME + " about the likelihood of rain " +
                "in a UK location today or tomorrow for a specific period. " + RainChancesControllerSupplier.EXAMPLE;
        return makeTellResponseWithCard(text);
    }

    private SpeechletResponse makeTellResponseWithCard(String text) {
        SimpleCard simpleCard = new SimpleCard();
        simpleCard.setTitle("Rain chances");
        simpleCard.setContent(text);
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(text);
        return SpeechletResponse.newTellResponse(outputSpeech, simpleCard);
    }
}
