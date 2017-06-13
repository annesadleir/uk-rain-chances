package uk.co.littlestickyleaves;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import uk.co.littlestickyleaves.control.RainChancesController;
import uk.co.littlestickyleaves.control.RainChancesControllerSupplier;
import uk.co.littlestickyleaves.domain.EnglishOutput;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.util.function.Supplier;

/**
 * This class is really just for testing the lambda
 * -- if you set this as the handler on AWS lambda, you can test the input and output with json
 * -- but for Alexa use, the Handler needs to be RainChanceSpeechletRequestStreamHandler
 * -- also could be improved by making it a stream handler so it does json better
 */
public class RainChancesHandler implements RequestHandler<RainQuery, EnglishOutput> {

    private Supplier<RainChancesController> rainChancesControllerSupplier;

    public RainChancesHandler() {
        this.rainChancesControllerSupplier = new RainChancesControllerSupplier();
    }

    @Override
    public EnglishOutput handleRequest(RainQuery rainQuery, Context context) {
        RainChancesController rainChancesController = rainChancesControllerSupplier.get();
        return rainChancesController.go(rainQuery);
    }
}
