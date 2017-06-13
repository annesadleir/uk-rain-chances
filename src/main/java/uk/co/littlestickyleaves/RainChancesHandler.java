package uk.co.littlestickyleaves;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.littlestickyleaves.control.RainChancesController;
import uk.co.littlestickyleaves.control.RainChancesControllerSupplier;
import uk.co.littlestickyleaves.domain.EnglishOutput;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

/**
 * This class is really just for testing the lambda
 * -- if you set this as the handler on AWS lambda, you can test the input and output with json
 * -- but for Alexa use, the Handler needs to be RainChanceSpeechletRequestStreamHandler
 */
public class RainChancesHandler implements RequestStreamHandler {

    private ObjectMapper objectMapper;
    private Supplier<RainChancesController> rainChancesControllerSupplier;

    public RainChancesHandler() {
        this.objectMapper = new ObjectMapper();
        this.rainChancesControllerSupplier = new RainChancesControllerSupplier();
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
       RainQuery rainQuery = objectMapper.readValue(inputStream, RainQuery.class);
       RainChancesController rainChancesController = rainChancesControllerSupplier.get();
       EnglishOutput englishOutput = rainChancesController.go(rainQuery);
       objectMapper.writeValue(outputStream, englishOutput);
    }
}
