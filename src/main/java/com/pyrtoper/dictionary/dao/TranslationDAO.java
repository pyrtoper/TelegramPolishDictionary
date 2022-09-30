package com.pyrtoper.dictionary.dao;

import com.pyrtoper.dictionary.entity.Translation;

import java.util.List;

public interface TranslationDAO {

    Translation getTranslationByName(String translationName);
    List<String> getSimilarTranslations(String initialWord);

}
