package com.pyrtoper.dictionary.constant;

import java.util.HashMap;
import java.util.Map;

public enum Numeral {
    SINGULAR("lp", "ед. ч"),
    PLURAL("lm", "мн. ч");

    private String numeral;

    private String russianNumeral;
    private static class Holder {
        static Map<String, Numeral> MAP = new HashMap<>();
    }

    Numeral(String numeral, String russianNumeral) {
        Holder.MAP.put(numeral, this);
        Holder.MAP.put(russianNumeral, this);
        this.numeral = numeral;
        this.russianNumeral = russianNumeral;
    }

    public String getNumeral() {
        return numeral;
    }

    public String getRussianNumeral() {
        return russianNumeral;
    }

    public static Numeral getNumeral(String numeral) {
        return Holder.MAP.get(numeral);
    }


}
