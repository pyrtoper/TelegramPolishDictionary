package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.entity.Translation;

import java.util.List;

public interface TranslationService {

    Translation getTranslationByName(String translationName);

    List<String> getSimilarTranslations(String initialWord);
}
