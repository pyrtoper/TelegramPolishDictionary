package com.pyrtoper.dictionary;
import com.pyrtoper.dictionary.constant.Declination;
import com.pyrtoper.dictionary.constant.Numeral;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;
import com.pyrtoper.dictionary.service.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {DictionaryApplication.class})
public class ServiceTests {
    @Autowired
    private WordService wordService;

    @Test
    void checkWordAttributes() {
        String name = "kozica";
        Word word = wordService.getWordByName(name);
        assertEquals(word.getName(), name);
        assertEquals(word.getTranslatedMeanings().getTranslatedMeaning(), Map.of("сущ., род ж.", List.of(
                "(1.1) (систематическое название: Rupicapra rupicapra), " +
                        "горное млекопитающее с пышным мехом и вертикально растущими рогами, " +
                        "загнутыми назад у самца;"
        )));
        assertEquals(word.getTranslationSet()
                .stream().map(Translation::getName).collect(Collectors.toSet()),
                Set.of("серна"));
        assertEquals(word.getWordForms().getWordForms(), Map.of("(1.1)",
                Map.of(Numeral.PLURAL, Map.of(Declination.MIANOWNIK, "kozice",
                        Declination.DOPEŁNIACZ, "kozic",
                        Declination.CELOWNIK, "kozicom",
                        Declination.BIERNIK, "kozice",
                        Declination.NARZĘDNIK, "kozicami",
                        Declination.MIEJSCOWNIK, "kozicach",
                        Declination.WOŁACZ, "kozice"),
                        Numeral.SINGULAR, Map.of(Declination.MIANOWNIK, "kozica",
                                Declination.DOPEŁNIACZ, "kozicy",
                                Declination.CELOWNIK, "kozicy",
                                Declination.BIERNIK, "kozicę",
                                Declination.NARZĘDNIK, "kozicą",
                                Declination.MIEJSCOWNIK, "kozicy",
                                Declination.WOŁACZ, "kozico"))));
    }

    @Test
    void getWordsByTranslation() {
        String name = "любить";
        Translation translation = wordService.getTranslationByName(name);
        assertEquals(Set.of("przać", "kochać", "lubić", "przoć", "ukochać", "gustować"),
                translation.getWordSet().stream().map(Word::getName).collect(Collectors.toSet())
                );
    }

    @Test
    void checkSimilarWords() {
        String name = "resz";
        List<String> similarWords = wordService.getSimilarWords(name);
        assertEquals(List.of("reszt", "reszka", "reszta", "Reszel", "resztka"),
                similarWords);
    }

    @Test
    void checkSimilarTranslations() {
        String name = "бан";
        List<String> similarTranslations = wordService.getSimilarTranslations(name);
        assertEquals(List.of("банан", "банк", "барабан", "балабан", "балобан"), similarTranslations);
    }
}
