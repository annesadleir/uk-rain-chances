package uk.co.littlestickyleaves.domain;

import java.util.stream.Stream;

/**
 * [Thing] to do [what] for [other]
 * -- stuff
 * -- more stuff
 */
// TODO Javadoc
public enum Percentages {

    LESS_THAN_FIVE("<5%", "less than five percent"),
    TEN("10%", "roughly ten percent"),
    TWENTY("20%", "roughly twenty percent"),
    THIRTY("30%", "roughly ten percent"),
    FORTY("40%", "roughly ten percent"),
    FIFTY("50%", "roughly ten percent"),
    SIXTY("60%", "roughly ten percent"),
    SEVENTY("70%", "roughly ten percent"),
    EIGHTY("80%", "roughly eighty percent"),
    NINETY("90%", "roughly ninety percent"),
    MORE_THAN_NINETY(">95%", "more than ninety-five percent");

    private String input;

    private String output;

    Percentages(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public static Percentages fromInput(String input) {
        return Stream.of(values())
                .filter(per -> input.equals(per.getInput()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Cannot interpret " + input));
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
