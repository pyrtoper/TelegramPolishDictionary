package com.pyrtoper.dictionary.dao;

import com.pyrtoper.dictionary.entity.Translation;
import com.pyrtoper.dictionary.entity.Word;

import java.util.List;
import java.util.Set;

public interface WordDAO {

    Word getWordByName(String wordName);

    Set<Translation> getTranslationByName(String translation);

    List<String> getSimilarWords(String initialWord);
}
