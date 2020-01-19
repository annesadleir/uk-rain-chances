package uk.co.littlestickyleaves.handlers;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

public class GenericExceptionHandler implements ExceptionHandler {

    private static final String EXCEPTION_MESSAGE = "There's been an error I cannot handle.  I apologise";

    @Override
    public boolean canHandle(HandlerInput handlerInput, Throwable throwable) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, Throwable throwable) {
        System.out.println("Handled " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage());
        return handlerInput.getResponseBuilder()
                .withSpeech(EXCEPTION_MESSAGE)
                .build();
    }
}
