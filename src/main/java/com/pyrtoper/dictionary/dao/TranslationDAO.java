package com.pyrtoper.dictionary.dao;

import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;

import java.util.List;
import java.util.Set;

public interface TranslationDAO {

    Translation getTranslationByName(String translationName);
    List<String> getSimilarTranslations(String initialWord);

    Set<Word> getPolishTranslations();
}
