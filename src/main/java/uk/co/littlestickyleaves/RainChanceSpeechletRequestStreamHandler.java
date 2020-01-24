package uk.co.littlestickyleaves;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import uk.co.littlestickyleaves.handlers.GenericExceptionHandler;

/**
 * The handler for Alexa use
 * -- sets up a SpeechletRequestStreamHandler with a specific Speechlet implementation
 * -- also specifies the Alexa App id, which it gets from an environment variable
 */
public class RainChanceSpeechletRequestStreamHandler extends SkillStreamHandler
//        SpeechletRequestStreamHandler
{

    public RainChanceSpeechletRequestStreamHandler() {
        super(getSkill());
    }

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers()
                .addExceptionHandler(new GenericExceptionHandler())
                .withAutoCreateTable(true)
                .withSkillId("skillId")
                .build();
    }

//    public RainChanceSpeechletRequestStreamHandler() {
//        super(new RainChancesSpeechlet(),
//                Sets.newHashSet(System.getenv("APP_ID")));
//    }
}
