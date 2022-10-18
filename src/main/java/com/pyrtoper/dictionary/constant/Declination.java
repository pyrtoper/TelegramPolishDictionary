package com.pyrtoper.dictionary.constant;

public enum Declination {
    MIANOWNIK("Mianownik"),
    DOPEŁNIACZ("Dopełniacz"),
    CELOWNIK("Celownik"),
    BIERNIK("Biernik"),
    NARZĘDNIK("Narzędnik"),
    MIEJSCOWNIK("Miejscownik"),
    WOŁACZ("Wołacz");


    private final String declination;

    Declination(String declination) {
        this.declination = declination;
    }

    public String getName() {
        return declination;
    }

}
