package com.pyrtoper.dictionary.constant;

public enum WorkState {
    POLISH_TO_RUSSIAN("Польский -> Русский"),
    RUSSIAN_TO_POLISH("Русский -> Польский");
    private final String workState;

    WorkState(String workState) {
        this.workState = workState;
    }

    public String getWorkState() {
        return workState;
    }
}
