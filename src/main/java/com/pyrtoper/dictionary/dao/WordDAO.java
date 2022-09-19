package com.pyrtoper.dictionary.dao;

import com.pyrtoper.dictionary.entity.Word;

import java.util.List;

public interface WordDAO {

    Word getWordByName(String wordName);

    Word getTranslationByName(String translation);

    List<String> getSimilarWords(String initialWord);
}
