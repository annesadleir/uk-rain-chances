package uk.co.littlestickyleaves.domain;

import java.util.stream.Stream;

/**
 * Defines the different percentage chances of rain on the Met Office mobile website
 * -- in severity order
 * -- has a reading-in value for web scraping
 * -- has an output value for speech representation
 */
public enum Percentages {

    LESS_THAN_FIVE("<5%", "less than five percent"),
    TEN("10%", "roughly ten percent"),
    TWENTY("20%", "roughly twenty percent"),
    THIRTY("30%", "roughly thirty percent"),
    FORTY("40%", "roughly forty percent"),
    FIFTY("50%", "roughly fifty percent"),
    SIXTY("60%", "roughly sixty percent"),
    SEVENTY("70%", "roughly seventy percent"),
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
