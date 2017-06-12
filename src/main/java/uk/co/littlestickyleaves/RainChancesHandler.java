package uk.co.littlestickyleaves;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import uk.co.littlestickyleaves.control.RainChancesController;
import uk.co.littlestickyleaves.control.RainChancesControllerSupplier;
import uk.co.littlestickyleaves.domain.EnglishOutput;
import uk.co.littlestickyleaves.domain.RainQuery;

import java.util.function.Supplier;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO Javadoc
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
