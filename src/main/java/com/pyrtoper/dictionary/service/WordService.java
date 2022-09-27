package com.pyrtoper.dictionary.service;

import com.pyrtoper.dictionary.dao.WordDAO;
import com.pyrtoper.dictionary.entity.MissingWord;
import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface WordService {
    Word getWordByName(String wordName);

    Translation getWordByTranslation(String translation);

    List<String> getSimilarWords(String initialWord);

    Translation getTranslationByName(String translationName);

    List<String> getSimilarTranslations(String initialWord);

    void saveMissingWord(MissingWord missingWord);

    MissingWord getMissingWordByName(String wordName);
}
