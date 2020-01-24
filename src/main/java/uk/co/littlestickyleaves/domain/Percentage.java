package uk.co.littlestickyleaves.domain;

import java.util.stream.Stream;

/**
 * Defines the different percentage chances of rain on the Met Office mobile website
 * -- in severity order
 * -- has a reading-in value for web scraping
 * -- has a min and max value for approximating specific percentage values
 * -- has an output value for speech representation
 */
public enum Percentage {

    LESS_THAN_FIVE("<5%", "less than five percent", 0, 4),
    TEN("10%", "roughly ten percent", 5, 14),
    TWENTY("20%", "roughly twenty percent", 15, 24),
    THIRTY("30%", "roughly thirty percent", 25, 34),
    FORTY("40%", "roughly forty percent", 35, 44),
    FIFTY("50%", "roughly fifty percent", 45, 54),
    SIXTY("60%", "roughly sixty percent", 55, 64),
    SEVENTY("70%", "roughly seventy percent", 65, 74),
    EIGHTY("80%", "roughly eighty percent", 75, 84),
    NINETY("90%", "roughly ninety percent", 85, 95),
    MORE_THAN_NINETY(">95%", "more than ninety-five percent", 96, 100);

    private String input;

    private String output;

    private int min;

    private int max;

    Percentage(String input, String output, int min, int max) {
        this.input = input;
        this.output = output;
        this.min = min;
        this.max = max;
    }

    public static Percentage fromInput(String input) {
        return Stream.of(values())
                .filter(per -> input.equals(per.getInput()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Cannot interpret " + input));
    }

    public static Percentage fromInput(int value) {
        return Stream.of(values())
                .filter(per -> value >= per.getMin() && value <= per.getMax())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Cannot interpret " + value + " as a percentage"));
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
