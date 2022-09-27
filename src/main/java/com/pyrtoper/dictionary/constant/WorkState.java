package com.pyrtoper.dictionary.constant;

import java.util.HashMap;
import java.util.Map;

public enum WorkState {
    POLISH_TO_RUSSIAN("Польский -> Русский"),
    RUSSIAN_TO_POLISH("Русский -> Польский");

    private String workState;

    private static class Holder {
        private static Map<String, WorkState> map = new HashMap<>();
    }
    WorkState(String workState) {
        Holder.map.put(workState, this);
        this.workState = workState;
    }

    public String getWorkState() {
        return workState;
    }
}
