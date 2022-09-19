package com.pyrtoper.dictionary.constant;

public enum PolishLetter {
    LETTER_ą("ą"),
    LETTER_ę("ę"),
    LETTER_ś("ś"),
    LETTER_ż("ż"),
    LETTER_ź("ź"),
    LETTER_ć("ć"),
    LETTER_ń("ń"),
    LETTER_ó("ó"),
    LETTER_ł("ł");


    private final String letter;

    PolishLetter(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }
}
