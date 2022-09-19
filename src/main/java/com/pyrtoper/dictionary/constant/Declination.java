package com.pyrtoper.dictionary.constant;

import java.util.HashMap;
import java.util.Map;

public enum Declination {
    MIANOWNIK("Mianownik"),
    DOPEŁNIACZ("Dopełniacz"),
    CELOWNIK("Celownik"),
    BIERNIK("Biernik"),
    NARZĘDNIK("Narzędnik"),
    MIEJSCOWNIK("Miejscownik"),
    WOŁACZ("Wołacz");


    private String declination;

    private static class Holder {
        private static Map<String, Declination> MAP = new HashMap<>();
    }
    Declination(String declination) {
        Holder.MAP.put(declination, this);
        this.declination = declination;
    }

    public String getName() {
        return declination;
    }

    public static Declination getDeclination(String name) {
        return Holder.MAP.get(name);
    }
}