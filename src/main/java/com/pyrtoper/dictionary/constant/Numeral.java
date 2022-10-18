package com.pyrtoper.dictionary.constant;

public enum Numeral {
    SINGULAR("lp", "ед. ч"),
    PLURAL("lm", "мн. ч");

    private final String numeral;

    private final String russianNumeral;

    Numeral(String numeral, String russianNumeral) {
        this.numeral = numeral;
        this.russianNumeral = russianNumeral;
    }

    public String getRussianNumeral() {
        return russianNumeral;
    }

    public String getNumeral() {
        return numeral;
    }
}
