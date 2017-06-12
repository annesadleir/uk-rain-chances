package uk.co.littlestickyleaves.domain;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO Javadoc
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
