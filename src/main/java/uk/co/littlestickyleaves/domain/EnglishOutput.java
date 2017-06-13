package uk.co.littlestickyleaves.domain;

/**
 * This is just a holder class to pass around text that's expected to be spoken as output by Alexa
 */
public class EnglishOutput {

    private String text;

    public EnglishOutput() {
    }

    public EnglishOutput(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
