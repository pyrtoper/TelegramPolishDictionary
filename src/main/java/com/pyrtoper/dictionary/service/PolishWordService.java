package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.entity.PolishWord;
import com.pyrtoper.dictionary.entity.Translation;

import java.util.List;
import java.util.Optional;

public interface PolishWordService {
    Optional<PolishWord> getWordByName(String wordName);

    List<String> getSimilarPolishWords(String initialWord);

    Optional<Translation> getTranslationByName(String translationName);

    List<String> getSimilarTranslations(String initialWord);

}
